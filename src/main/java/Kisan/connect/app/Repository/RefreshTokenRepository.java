package Kisan.connect.app.Repository;

import Kisan.connect.app.entities.RefreshToken;
import Kisan.connect.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    
    // Add this method to find existing token by user
    Optional<RefreshToken> findByUser(Users users);
    
    void deleteByUser(Users users);
}