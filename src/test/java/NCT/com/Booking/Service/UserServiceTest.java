package NCT.com.Booking.Service;

import NCT.com.Booking.DTO.Request.UserCreateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService ;
    @Autowired
    private MockMvc mockMvc;

    private UserCreateRequest userCreateRequest ;
    private UserResponse userResponse ;
    private Users users ;

    @BeforeEach
    public void initData () {
        List<String> roles = List.of("User") ;
        Set<String> Setroles = Set.of("User") ;
        userCreateRequest = UserCreateRequest.builder()
                .firstName("Tam")
                .lastName("Nguyen Chi")
                .userName("Tam123")
                .passWord("123456")
                .roles(roles)
                .build() ;

        userResponse = UserResponse.builder()
                .firstName("Tam")
                .lastName("Nguyen Chi")
                .userName("Tam123")
                .roles(Setroles)
                .build();
    }

    @Test
    void CreateUser_validRequest_success() {
        //given
    }
}
