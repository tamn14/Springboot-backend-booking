package NCT.com.Booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;
    @Column(name = "name")
    private String name ;
    @ManyToMany(
            mappedBy = "roles" ,
            fetch = FetchType.EAGER ,
            cascade = {
                    CascadeType.DETACH ,
                    CascadeType.MERGE ,
                    CascadeType.PERSIST ,
                    CascadeType.REFRESH
            }

    )
    private List<Users> users = new ArrayList<>() ;
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
            name = "role-permission" ,
            joinColumns = @JoinColumn(name = "roleId") ,
            inverseJoinColumns =  @JoinColumn(name = "permissionId")
    )
    private List<Permission> permissions = new ArrayList<>() ;


    // Synchronizes data
    public void addPermission(Permission permission) {
        this.permissions.add(permission) ;
        permission.getRoles().add(this) ;
    }

    public void deletePermission(Permission permission) {
        this.permissions.remove(permission) ;
        permission.getRoles().remove(this) ;
    }

}
