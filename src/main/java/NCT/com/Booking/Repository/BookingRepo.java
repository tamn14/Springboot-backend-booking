package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Booking , Integer> {
}
