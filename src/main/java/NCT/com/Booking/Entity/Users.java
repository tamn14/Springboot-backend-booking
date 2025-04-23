package NCT.com.Booking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;
    @Column(name = "username", length = 25)
    private String userName ;
    @Column(name = "password")
    private String passWord ;
    @Column(name = "lastname")
    private String lastName ;
    @Column(name = "firstname")
    private String firstName ;
    @ManyToMany(
            fetch =  FetchType.LAZY ,
            cascade = {
                    CascadeType.DETACH ,
                    CascadeType.MERGE ,
                    CascadeType.PERSIST ,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "user-role" ,
            joinColumns = @JoinColumn(name = "userId") ,
            inverseJoinColumns =  @JoinColumn(name = "roleId")
    )
    private List<Roles> roles = new ArrayList<>();

    @OneToMany(
            fetch =  FetchType.LAZY ,
            cascade = {
                   CascadeType.ALL
            } ,
            mappedBy = "users"
    )
    private List<Booking> bookings = new ArrayList<>() ;

    // Synchronizes data
    public void addRole(Roles role) {
        this.roles.add(role) ;
        role.getUsers().add(this);
    }

    public void deleteRole(Roles role) {
        this.roles.remove(role) ;
        role.getUsers().remove(this);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking) ;
        booking.setUsers(this);
    }

    public void deleteBooking(Booking booking) {
        this.bookings.remove(booking) ;
        booking.setUsers(null);
    }

}
