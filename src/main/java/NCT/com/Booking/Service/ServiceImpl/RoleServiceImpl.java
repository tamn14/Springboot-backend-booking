package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.RolesCreationRequest;
import NCT.com.Booking.DTO.Request.RolesUpdateRequest;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.RoleMapper;
import NCT.com.Booking.Repository.PermissionRepo;
import NCT.com.Booking.Repository.RoleRepo;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.RoleService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleRepo roleRepo ;
    private RoleMapper roleMapper ;
    private UserRepo userRepo ;
    private PermissionRepo permissionRepo ;
    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo, RoleMapper roleMapper, UserRepo userRepo, PermissionRepo permissionRepo) {
        this.roleRepo = roleRepo;
        this.roleMapper = roleMapper;
        this.userRepo = userRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public RoleResponse addRoles(RolesCreationRequest roleRequest) {
        Roles roles = roleMapper.toEntity(roleRequest) ;
        if(roleRepo.findByName(roleRequest.getName()) != null) {
            throw new AppException(ErrorCode.ROLES_EXISTED);
        }
        roleRepo.saveAndFlush(roles) ;
        return roleMapper.toDTO(roles) ;

    }

    @Override
    public RoleResponse updateRoles(RolesUpdateRequest roleRequest, int id) {
        Roles roleUpdate = roleRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXISTED)) ;
        roleUpdate.setName(roleRequest.getName());
        Set<Users> users = new HashSet<>() ;
        Set<Permission> permissions = new HashSet<>() ;
        if(!roleRequest.getUsers().isEmpty()) {
            roleRequest.getUsers().forEach(user->{
                Users usersExisted = userRepo.findById(user)
                        .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
                users.add(usersExisted) ;

            });
            // xoa quan he giua role va user cu
            roleUpdate.getUsers().forEach(userOld -> {
                roleUpdate.getUsers().remove(userOld) ;
                userOld.getRoles().remove(roleUpdate) ;
            });
            // them quan he giua role va user
            users.forEach(user-> {
                roleUpdate.getUsers().add(user) ;
                user.getRoles().add(roleUpdate) ;
            });

        }
        if(!roleRequest.getPermissions().isEmpty()) {
            roleRequest.getPermissions().forEach(permission->{
                Permission permissionExisted = permissionRepo.findByName(permission) ;
                if(permissionExisted == null) {
                    throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED) ;
                }
                permissions.add(permissionExisted) ;
            });
            // xoa quan he giua role va permission cu
            roleUpdate.getPermissions().forEach(perOld -> {
                roleUpdate.getPermissions().remove(perOld) ;
                perOld.getRoles().remove(roleUpdate) ;
            });
            // them quan he giua role va permission
            permissions.forEach(perNew-> {
                roleUpdate.getPermissions().add(perNew) ;
                perNew.getRoles().add(roleUpdate) ;
            });
        }

        roleRepo.saveAndFlush(roleUpdate) ;
        return roleMapper.toDTO(roleUpdate) ;

    }

    @Override
    public void deleteRoles(int id) {
        Roles roles = roleRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXISTED)) ;
        roleRepo.deleteById(id) ;
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepo.findAll().stream()
                .map(roleMapper :: toDTO).toList() ;
    }

    @Override
    public RoleResponse findroleById(int id) {
        Roles roles = roleRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXISTED)) ;
        return roleMapper.toDTO(roles) ;
    }
}
