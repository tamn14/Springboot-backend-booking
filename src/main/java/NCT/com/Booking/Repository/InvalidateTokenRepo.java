package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepo extends JpaRepository<InvalidatedToken , String> {
    public boolean existsById(String id);
}
