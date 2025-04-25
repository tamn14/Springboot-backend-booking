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
public class UserServiceImpl  {



}
