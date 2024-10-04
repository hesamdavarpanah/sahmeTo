package com.backend.sahmeto.authentication.contollers;

import com.backend.sahmeto.authentication.domains.UserData;
import com.backend.sahmeto.authentication.models.ERole;
import com.backend.sahmeto.authentication.models.Role;
import com.backend.sahmeto.authentication.models.User;
import com.backend.sahmeto.authentication.payloads.requests.CheckUserExist;
import com.backend.sahmeto.authentication.payloads.requests.OTPValidation;
import com.backend.sahmeto.authentication.payloads.requests.SignIn;
import com.backend.sahmeto.authentication.payloads.responses.JwtResponse;
import com.backend.sahmeto.authentication.payloads.responses.MessageResponse;
import com.backend.sahmeto.authentication.repositories.RoleRepository;
import com.backend.sahmeto.authentication.repositories.UserRepository;
import com.backend.sahmeto.authentication.securities.JwtUtils;
import com.backend.sahmeto.authentication.services.RedisSender;
import com.backend.sahmeto.authentication.services.UserDetailImplement;
import com.backend.sahmeto.authentication.services.UserService;
import com.backend.sahmeto.authentication.validators.CheckOTPValidationValidator;
import com.backend.sahmeto.authentication.validators.CheckUserExistValidator;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(value = "/api/auth", produces = "application/json", consumes = "application/json")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisSender redisSender;

    @Autowired
    private CheckUserExistValidator checkUserExistValidator;

    @Autowired
    private CheckOTPValidationValidator checkOTPValidationValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    private final String password = UUID.randomUUID().toString();
    private final Random random = new Random();
    private final int smsCode = random.nextInt(999999 - 100000) + 100000;
    private final LocalDateTime deadTime = LocalDateTime.now().plusMinutes(2);

    @RateLimiter(name = "anonymousAuth")
    @PostMapping("/check-user-exist/")
    public ResponseEntity<?> checkUserExist(@Valid @RequestBody CheckUserExist checkUserExist) {
        try {
            String phoneNumber = checkUserExist.getPhoneNumber();
            if (checkUserExistValidator.checkPhoneNumber(phoneNumber).getStatusCode() != HttpStatus.OK) {
                return checkUserExistValidator.checkPhoneNumber(phoneNumber);
            }
            if (!userService.checkUserExist(phoneNumber)) {
                UserData userData = new UserData(phoneNumber, smsCode, deadTime.toString());
                redisSender.saveData(phoneNumber, userData);
                return MessageResponse.generateResponse("not exist",
                        "the user with this phone number does not exist, sms code sent.", HttpStatus.ACCEPTED);
            }
            return MessageResponse.generateResponse("exist", "the user with this phone number exist!", HttpStatus.FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RateLimiter(name = "anonymousAuth")
    @PostMapping("/otp-validation/")
    public ResponseEntity<?> checkOTPValidation(@Valid @RequestBody OTPValidation otpValidation) {
        String phoneNumber = otpValidation.getPhoneNumber();
        int smsCode = otpValidation.getSmsCode();
        System.out.println(password);
        if (checkOTPValidationValidator.checkOTP(phoneNumber, smsCode).getStatusCode() != HttpStatus.OK) {
            return checkOTPValidationValidator.checkOTP(phoneNumber, smsCode);
        }
        User user = new User(phoneNumber, phoneNumber, passwordEncoder.encode(password), null, null, 0, 0, LocalDateTime.now(), LocalDateTime.now());

        Set<String> setRoles = otpValidation.getRole();
        Set<Role> roles = new HashSet<>();
        if (setRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            setRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is nor found"));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImplement userDetailImplement = (UserDetailImplement) authentication.getPrincipal();
        List<String> otpRoles = userDetailImplement.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetailImplement.getId(), userDetailImplement.getUsername(), userDetailImplement.getPhoneNumber(), otpRoles));
    }

    @RateLimiter(name = "anonymousAuth")
    @PostMapping("/signin/")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignIn signIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailImplement userDetailImplement = (UserDetailImplement) authentication.getPrincipal();
            List<String> roles = userDetailImplement.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

            return ResponseEntity.ok(new JwtResponse(jwt, userDetailImplement.getId(), userDetailImplement.getUsername(), userDetailImplement.getPhoneNumber(), roles));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
