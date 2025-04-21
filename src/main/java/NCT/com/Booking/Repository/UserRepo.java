package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users , Integer> {
}
