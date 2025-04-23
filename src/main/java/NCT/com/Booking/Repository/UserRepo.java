package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users , Integer> {
    boolean existsByUserName(String userName);

    Optional<Users> findByUserName(String userName);
}
