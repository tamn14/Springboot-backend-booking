package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission , Integer> {
}
