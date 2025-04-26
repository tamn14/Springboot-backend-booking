package NCT.com.Booking.DTO.Response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String name ;
    private Set<Integer> users  ;
    private Set<String> permissions  ;
}
