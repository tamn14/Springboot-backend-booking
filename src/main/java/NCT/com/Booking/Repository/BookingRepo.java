package NCT.com.Booking.Repository;

import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking , Integer> {
    public List<Booking> findAllByUsers(Users users) ;
}
