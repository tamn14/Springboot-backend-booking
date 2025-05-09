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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder ;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addUsers(UserCreateRequest userRequest) {
        Users users = userMapper.toEntity(userRequest);
        if(userRepo.existsByUserName(users.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED) ;
        }
        users.setPassWord(passwordEncoder.encode(userRequest.getPassWord()));
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
        userRepo.saveAndFlush(users) ;
        return userMapper.toDTO(users);


    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUsers(UsersUpdateRequest userRequest, int id) {
        Users userUpdate = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
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
            Set<Roles> oldRoles = new HashSet<>(userUpdate.getRoles());
            for (Roles roleOld : oldRoles) {
                userUpdate.getRoles().remove(roleOld);
                roleOld.getUsers().remove(userUpdate);
            }
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
    @PreAuthorize("hasRole('ADMIN')")
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
    public UserResponse findUserById(int id) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toDTO(user) ;
    }

    @Override
    public UserResponse findByUserName(String userName) {
        Users users =  userRepo.findByUserName(userName) ;
        if(users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return userMapper.toDTO(users) ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepo.findByUserName(username) ;
        if(users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED) ;
        }
        User user = new User(
                users.getUserName(),
                users.getPassWord() ,
                roleToAuthorities(users.getRoles())
        ) ;
        return user ;
    }

    private Collection<? extends GrantedAuthority> roleToAuthorities (Collection<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getName()
                )).collect(Collectors.toList());
    }

    @Override
    public UserResponse getMyInfor() {
        var context = SecurityContextHolder.getContext() ;
        String name = context.getAuthentication().getName() ;
        Users user = userRepo.findByUserName(name) ;
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED) ;
        }
        return userMapper.toDTO(user) ;
    }

    @Override
    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse UpdateMyUser(UsersUpdateRequest userRequest, int id) {
        var context = SecurityContextHolder.getContext() ;
        String name = context.getAuthentication().getName() ;
        Users userUpdate = userRepo.findByUserName(name) ;
        if(userUpdate == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED) ;
        }
        userUpdate.setLastName(userRequest.getLastName());
        userUpdate.setFirstName(userRequest.getFirstName());
        userUpdate.setPassWord(passwordEncoder.encode(userRequest.getPassWord()));
        userRepo.saveAndFlush(userUpdate) ;
        return userMapper.toDTO(userUpdate) ;
    }
}
