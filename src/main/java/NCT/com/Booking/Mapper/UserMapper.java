package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.UserCreateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private  RoleRepo roleRepo;
    @Autowired
    public UserMapper(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    // Convert Request DTO -> Entity
    public Users toEntity(UserCreateRequest request) {
        Users users = new Users();
        users.setLastName(request.getLastName());
        users.setFirstName(request.getFirstName());
        users.setUserName(request.getUserName());
        users.setPassWord(request.getPassWord());

        Set<Roles> roleEntities = new HashSet<>();
        if (request.getRoles() != null) {
            for (String roleName : request.getRoles()) {
                Roles roleEntity = roleRepo.findByName(roleName);
                if (roleEntity != null) {
                    roleEntities.add(roleEntity);
                }
            }
        }

        users.setRoles(roleEntities);
        return users;
    }

    // Convert Entity -> Response DTO
    public UserResponse toDTO(Users users) {
        UserResponse response = new UserResponse();
        response.setLastName(users.getLastName());
        response.setFirstName(users.getFirstName());
        response.setUserName(users.getUserName());
        response.setPassWord(users.getPassWord());

        Set<String> roleNames = users.getRoles().stream()
                .map(Roles::getName)
                .collect(Collectors.toSet());

        List<Integer> bookingIds = users.getBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        response.setRoles(roleNames);
        response.setBookings(bookingIds);
        return response;
    }
}
