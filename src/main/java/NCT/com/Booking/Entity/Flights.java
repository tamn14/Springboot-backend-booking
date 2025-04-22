package NCT.com.Booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;
    @Column(name = "flight-number")
    private String flightNumber ;
    @Column(name = "from-location")
    private String fromLocation ;
    @Column(name = "to-location")
    private String toLocation ;
    @Column(name = "departure-time")
    private LocalDateTime departureTime;
    @Column(name = "arrival-time")
    private LocalDateTime arrivalTime ;
    @Column(name = "airline")
    private String airline ;
    @Column(name = "price")
    private double price ;
    @Column(name = "available-seats")
    private int availableSeats ;

    @OneToMany(
            mappedBy = "flights" ,
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH ,
                    CascadeType.MERGE ,
                    CascadeType.PERSIST ,
                    CascadeType.REFRESH
            }

    )
    private List<Booking> bookings = new ArrayList<>();
    // Synchronizes data
    public void addBooking(Booking booking) {
        this.bookings.add(booking) ;
        booking.setFlights(this);
    }

    public void deleteBooking(Booking booking) {
        this.bookings.remove(booking) ;
        booking.setFlights(null);
    }
}
