package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users , Integer> {
    public boolean existsByUserName(String userName);
    public Users findByUserName(String userName);


}
