package NCT.com.Booking.Entity;

import NCT.com.Booking.DTO.Response.PermissionResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "roles")
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
    private Set<Users> users = new HashSet<>() ;
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
    private Set<Permission> permissions = new HashSet<>() ;

    // Synchronizes data
    public void addPermission(Permission permission) {
        this.permissions.add(permission) ;
        permission.getRoles().add(this);
    }

    public void deletePermission(Permission permission) {
        this.permissions.remove(permission) ;
        permission.getRoles().remove(this);
    }



}
