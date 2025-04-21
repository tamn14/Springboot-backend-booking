package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles , Integer> {
}
