package NCT.com.Booking.DTO.Reponse;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiReponse <T>{
    private int code ;
    private String mess ;
    private T result ;
}
