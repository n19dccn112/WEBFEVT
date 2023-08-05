package com.n19dccn112.service;

import com.n19dccn112.model.Auth.*;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Role;
import com.n19dccn112.model.entity.User;
import com.n19dccn112.model.entity.UserDetail;
import com.n19dccn112.repository.RoleRepository;
import com.n19dccn112.repository.UserDetailRepository;
import com.n19dccn112.repository.UserRepository;
import com.n19dccn112.security.jwt.JwtUtils;
import com.n19dccn112.security.services.UserDetailsImpl;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IBaseService<UserDTO, Long>, IModelMapper<UserDTO, User> {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository, RoleRepository roleRepository, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return createFromEntities(userRepository.findAll());
    }

    @Override
    public UserDTO findById(Long userId) {
        Optional <User> user =  userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException(UserDTO.class, userId));
        return createFromE(user.get());
    }

    @Override
    public UserDTO update(Long userId, UserDTO userDTO) {
        Optional <User> user =  userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException(UserDTO.class, userId));
        userRepository.save(updateEntity(user.get(), userDTO));
        return createFromE(user.get());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userRepository.save(createFromD(userDTO));
        Optional<User> user = userRepository.findUserByUserName(userDTO.getUsername());

        Optional<Role> role = roleRepository.findRoleByRoleName(userDTO.getRole().name());
        role.orElseThrow(() -> new NotFoundException(UserDTO.class, userDTO.getUserId()));
        user.get().setRole(role.get());

        userRepository.save(user.get());

        return createFromE(user.get());
    }

    @Override
    public UserDTO delete(Long userId) {
        Optional <User> user =  userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException(UserDTO.class, userId));
        UserDTO userDTO = createFromE(user.get());
        try {
            userRepository.delete(user.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, userId);
        }
        return userDTO;
    }

    @Override
    public User createFromD(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        return user;
    }

    @Override
    public UserDTO createFromE(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if(user.getUserDetails() != null) {
            for (UserDetail userDetail : user.getUserDetails()) {
                if (userDetail.getAddressDefault() == 1) {
                    userDTO.setUserDetailId(userDetail.getUserDetailId());
                    userDTO.setAddress(userDTO.getAddress());
                    userDTO.setName(userDTO.getName());
                    userDTO.setAddressDefault(userDetail.getAddressDefault());
                }
            }
        }
        if (user.getRole() != null) {
            userDTO.setRole(user.getRole().getName());
        }
        userDTO.setPassword("");
        return userDTO;
    }

    @Override
    public User updateEntity(User user, UserDTO userDTO) {
        if (user != null && userDTO != null){
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setUsername(userDTO.getUsername());
            user.setPhone(userDTO.getPhone());
        }
        return user;
    }

    public ResponseEntity<JwtResponse> checkLogin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // if go there, the user/password is correct
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt to return to client
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.getById(userDetails.getId());
        List<String> roles = List.of(user.getRole().getName().name());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPhone(),
                userDetails.getEmail(),
                roles.get(0)));
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Tên đã tồn tại!"));
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Email đã tồn tại!"));
        }
        // Create new user's account
        UserDTO userDTO = modelMapper.map(registerRequest, UserDTO.class);
        userDTO.setRole(registerRequest.getRolename());
        save(userDTO);
        return new ResponseEntity<>(new MessageResponse("Đăng ký thành công!"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> changePassByEmail(ChangePassByEmailRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Email không tìm thấy!"));
        }
        User user = userRepository.findByEmail(request.getEmail()).get();
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Thay đổi mật khẩu thành công!"));
    }
}
