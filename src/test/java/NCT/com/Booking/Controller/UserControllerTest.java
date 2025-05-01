package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.UserCreateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private UserCreateRequest userCreateRequest ;
    private UserResponse userResponse ;

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
    @WithMockUser(username = "Tam123", roles = {"USER"})
    //
    void createUser_validRequest_success() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper() ;
        String content = objectMapper.writeValueAsString(userCreateRequest) ;

        Mockito.when(userService.addUsers(ArgumentMatchers.any(UserCreateRequest.class)))
                .thenReturn(userResponse);
        // When , Then
        mockMvc.perform(MockMvcRequestBuilders.
                post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000)
        ) ;


    }


    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }
}
