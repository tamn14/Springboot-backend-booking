package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.UserCreateRequest;
import NCT.com.Booking.DTO.Request.UsersUpdateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.UserMapper;
import NCT.com.Booking.Repository.BookingRepo;
import NCT.com.Booking.Repository.RoleRepo;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse addUsers(UserCreateRequest userRequest) {
        Users users = userMapper.toEntity(userRequest);
        if (!userRequest.getRoles().isEmpty()) {
            userRequest.getRoles().forEach(role -> {
                Roles rolesExisted = roleRepo.findByName(role);
                if (rolesExisted == null) {
                    throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
                }
                // them role vao user va user vao role
                users.getRoles().add(rolesExisted);
                rolesExisted.getUsers().add(users);
            });
        }
        return userMapper.toDTO(users);


    }

    @Override
    public UserResponse updateUsers(UsersUpdateRequest userRequest, int id) {
        Users userUpdate = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userUpdate.setLastName(userRequest.getLastName());
        userUpdate.setFirstName(userRequest.getFirstName());
        userUpdate.setUserName(userRequest.getUserName());
        userUpdate.setPassWord(userRequest.getPassWord());
        Set<Roles> roles = new HashSet<>();
        if (!userRequest.getRoles().isEmpty()) {
            userRequest.getRoles().forEach(role -> {
                Roles rolesExisted = roleRepo.findByName(role);
                if (rolesExisted == null) {
                    throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
                }
                roles.add(rolesExisted);
            });
            // xoa moi quan he giua role va userUpdate de cap nhat role moi
            userUpdate.getRoles().forEach( roleOld -> {
                userUpdate.getRoles().remove(roleOld);
                roleOld.getUsers().remove(userUpdate) ;
            });
            // cap nhat role moi cho userUpdate
            roles.forEach(roleNew -> {
                userUpdate.getRoles().add(roleNew);
                roleNew.getUsers().add(userUpdate) ;
            });
        }
        userRepo.saveAndFlush(userUpdate) ;
        return userMapper.toDTO(userUpdate) ;
    }

    @Override
    public void deleteUsers(int id) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepo.deleteById(id);

    }

    @Override
    public List<UserResponse> findAll() {
        return userRepo.findAll().stream()
                .map(userMapper::toDTO).toList() ;
    }

    @Override
    public UserResponse findFlightById(int id) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toDTO(user) ;
    }
}
