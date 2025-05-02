package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Mapper.PermissionMapper;
import NCT.com.Booking.Repository.PermissionRepo;
import NCT.com.Booking.Repository.RoleRepo;
import NCT.com.Booking.Service.ServiceInterface.PermissionService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepo permissionRepo ;
    private RoleRepo roleRepo ;
    private PermissionMapper permissionMapper ;
    @Autowired
    public PermissionServiceImpl(PermissionRepo permissionRepo, RoleRepo roleRepo, PermissionMapper permissionMapper) {
        this.permissionRepo = permissionRepo;
        this.roleRepo = roleRepo;
        this.permissionMapper = permissionMapper;
    }



    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse addPermission(PermissionRequest permissionRequest) {
        Permission permissionCreation = permissionMapper.toEntity(permissionRequest) ;
        if(permissionRepo.findByName(permissionRequest.getName()) != null) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED) ;

        }
        permissionRequest.getRoles().forEach(role-> {
            Roles roles = roleRepo.findByName(role) ;
            if(roles == null) {
                throw new AppException(ErrorCode.ROLE_NOT_EXISTED) ;
            }
            roles.getPermissions().add(permissionCreation) ;
            permissionCreation.getRoles().add(roles);
        });

        permissionRepo.saveAndFlush(permissionCreation) ;
        return permissionMapper.toDTO(permissionCreation);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse updatePermission(PermissionRequest permissionRequest , int id ) {
        Permission permissionUpdate = permissionRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)) ;
        permissionUpdate.setName(permissionRequest.getName());
        Set<Roles> roles = new HashSet<>() ;
        if(!permissionRequest.getRoles().isEmpty()) {
            permissionRequest.getRoles().forEach(role -> {
                Roles rolesExisted = roleRepo.findByName(role) ;
                if(rolesExisted == null) {
                    throw new AppException(ErrorCode.ROLE_NOT_EXISTED) ;
                }
                roles.add(rolesExisted) ;
            });
            Set<Roles> oldRoles = new HashSet<>(permissionUpdate.getRoles());
            for (Roles roleOld : oldRoles) {
                permissionUpdate.getRoles().remove(roleOld);
                roleOld.getPermissions().remove(permissionUpdate);
            }
            roles.forEach(roleNew -> {
                permissionUpdate.getRoles().add(roleNew) ;
                roleNew.getPermissions().add(permissionUpdate) ;
            });


        }

        permissionRepo.saveAndFlush(permissionUpdate) ;
        return permissionMapper.toDTO(permissionUpdate)  ;

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(int id) {
        Permission permission = permissionRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)) ;
        Set<Roles> rolesToRemove = new HashSet<>(permission.getRoles());
        for (Roles role : rolesToRemove) {
            role.getPermissions().remove(permission);
        }
        permission.getRoles().clear();
        permissionRepo.deleteById(id);

    }

    @Override
    public List<PermissionResponse> findAll() {
        return permissionRepo.findAll().stream().map(permissionMapper::toDTO).toList() ;
    }

    @Override
    public PermissionResponse findPermissionById(int id) {
        Permission permission = permissionRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)) ;
        return permissionMapper.toDTO(permission);
    }
}
