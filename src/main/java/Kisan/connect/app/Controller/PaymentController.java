package Kisan.connect.app.Controller;



import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import Kisan.connect.app.Repository.PaymentRepository;
import Kisan.connect.app.entities.Payment;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private PaymentRepository paymentRepository;

    // STEP 1: Create Order & Save initial record to DB
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {
            int amountInPaise = Integer.parseInt(data.get("amount").toString());
            
            if (amountInPaise < 100) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Minimum amount must be at least 100 paise"));
            }

            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", data.getOrDefault("currency", "INR"));
            orderRequest.put("receipt", data.getOrDefault("receipt", "txn_123456"));

            Order razorpayOrder = razorpay.orders.create(orderRequest);

            // Save payment tracking entry into database
            Payment payment = new Payment();
            payment.setRazorpayOrderId(razorpayOrder.get("id"));
            payment.setAmount(amountInPaise / 100.0); // Convert paise to rupees for storage
            payment.setCurrency(razorpayOrder.get("currency"));
            payment.setStatus("CREATED");
            paymentRepository.save(payment);

            return ResponseEntity.ok(Map.of(
                "order_id", razorpayOrder.get("id"),
                "amount", razorpayOrder.get("amount"),
                "currency", razorpayOrder.get("currency")
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // STEP 2: Verify Signature & Update DB Status
    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
        try {
            String orderId = data.get("razorpay_order_id");
            String paymentId = data.get("razorpay_payment_id");
            String razorpaySignature = data.get("razorpay_signature");

            if (orderId == null || paymentId == null || razorpaySignature == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Missing required fields"));
            }

            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", razorpaySignature);

            boolean isSignatureValid = Utils.verifyPaymentSignature(options, keySecret);

            Optional<Payment> optionalPayment = paymentRepository.findByRazorpayOrderId(orderId);

            if (isSignatureValid) {
                // Update payment record as success
                optionalPayment.ifPresent(payment -> {
                    payment.setRazorpayPaymentId(paymentId);
                    payment.setRazorpaySignature(razorpaySignature);
                    payment.setStatus("SUCCESS");
                    paymentRepository.save(payment);
                });

                return ResponseEntity.ok(Map.of("success", true, "message", "Payment verified successfully"));
            } else {
                // Update payment record as failed due to signature mismatch
                optionalPayment.ifPresent(payment -> {
                    payment.setStatus("FAILED");
                    paymentRepository.save(payment);
                });

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Signature mismatch. Verification failed."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}