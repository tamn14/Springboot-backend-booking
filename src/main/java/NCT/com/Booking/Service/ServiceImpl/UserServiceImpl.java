package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.RoleMapper;
import NCT.com.Booking.Mapper.UserMapper;
import NCT.com.Booking.Repository.RoleRepo;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepo userRepo ;
    private UserMapper userMapper ;
    private PasswordEncoder passwordEncoder ;
    private RoleRepo roleRepo ;
    private RoleMapper roleMapper ;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepo roleRepo  ) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }


    @Override
    public UserResponse addUsers(UserRequest userRequest) {
        // Nếu đã tồn tại username thì ném lỗi
        userRepo.findByUserName(userRequest.getUserName())
                .ifPresent(u -> { throw new AppException(ErrorCode.USER_EXISTED); });

        // Map từ request sang entity
        Users user = userMapper.toUser(userRequest);
        user.setPassWord(passwordEncoder.encode(userRequest.getPassWord()));

        // Gán roles nếu có
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            for (Integer roleId : userRequest.getRoles()) {
                Roles role = roleRepo.findById(roleId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
                user.addRole(role);
            }
        }

        // Lưu và trả về response
        userRepo.save(user);
        return userMapper.toUserResponse(user);
    }


    @Override
    public UserResponse updateUsers(UserRequest userRequest , int id) {
        Users userUpdate = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
        userUpdate.setFirstName(userRequest.getFirstName());
        userUpdate.setLastName(userRequest.getLastName()) ;
        userUpdate.setUserName(userRequest.getUserName());
        userUpdate.setPassWord(passwordEncoder.encode(userRequest.getPassWord()));
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            for (Integer roleId : userRequest.getRoles()) {
                Roles role = roleRepo.findById(roleId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
                userUpdate.deleteRole(role);
                userUpdate.addRole(role);
            }
        }
        userRepo.saveAndFlush(userUpdate) ;
        return userMapper.toUserResponse(userUpdate);

    }

    @Override
    public void deleteUsers(int id) {

    }

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse findFlightById(int id) {
        return null;
    }
}
