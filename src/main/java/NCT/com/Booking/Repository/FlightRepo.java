package NCT.com.Booking.Repository;

import NCT.com.Booking.Entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends JpaRepository<Flights , Integer> {
}
