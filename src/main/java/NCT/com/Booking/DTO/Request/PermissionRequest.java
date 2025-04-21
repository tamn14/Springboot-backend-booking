package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Roles;
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
public class PermissionRequest {

    private String name ;
    private List<Integer> roles ;
}
