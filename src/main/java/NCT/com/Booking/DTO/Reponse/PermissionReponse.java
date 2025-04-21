package NCT.com.Booking.DTO.Reponse;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionReponse {

    private String name ;
    private List<Integer> roles ;
}
