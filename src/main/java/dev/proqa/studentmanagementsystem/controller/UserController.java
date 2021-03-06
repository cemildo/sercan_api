package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.security.jwt.JwtUtils;
import dev.proqa.studentmanagementsystem.service.StudentService;
import dev.proqa.studentmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping()
@Slf4j
public class UserController {

    public UserService userService;
    public StudentService studentService;
    public AuthenticationManager authenticationManager;
    public JwtUtils jwtUtils;


    @GetMapping("/user/all/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.fetchAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/user/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByAdminId(@PathVariable Long id) {
        UserDTO userDTO = userService.findById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getCurrentUserById(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("id");
        UserDTO userDTO = userService.findById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> registerUser( @RequestBody User user) {

        userService.register(user);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody Map<String, Object> userMap) {
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");

        userService.login(username, password);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/user/update")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updateUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {

        Long id = (Long) request.getAttribute("id");
        userService.updateUser(id, userDTO);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

}

