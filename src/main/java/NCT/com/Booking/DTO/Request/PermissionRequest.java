 package NCT.com.Booking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {
    private String name ;
    private Set<String> roles;

}
