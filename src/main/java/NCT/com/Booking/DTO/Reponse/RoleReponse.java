package NCT.com.Booking.DTO.Reponse;

import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Users;
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
public class RoleReponse {
    private String name ;
    private List<Integer> users  ;
    private List<Integer> permissions  ;
}
