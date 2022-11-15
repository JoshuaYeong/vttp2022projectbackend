package vttp2022.finalprojectbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.finalprojectbackend.configs.JwtUtil;
import vttp2022.finalprojectbackend.models.JwtRequest;
import vttp2022.finalprojectbackend.models.JwtResponse;
import vttp2022.finalprojectbackend.services.AppUserService;
import vttp2022.finalprojectbackend.services.CustomUserDetailsService;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AppUserService appUserSvc;

    @Autowired
    private CustomUserDetailsService customUserDetailsSvc;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest jwtRequest) {

        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();

        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailsSvc.loadUserByUsername(username);

            String jwtToken = jwtUtil.generateToken(userDetails);
            // logger.info(">>>>>Token Generated: " + jwtToken);

            JwtResponse jwtResponse = new JwtResponse(jwtToken, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(jwtResponse);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(">>>>>Error: Invalid Username or Password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody JwtRequest jwtRequest) {

        if (appUserSvc.userExists(jwtRequest.getUsername())) {
            // logger.info(">>>>>User exists: {} ", jwtRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(">>>>>Error: Username is already taken!");
        }

        try {
            appUserSvc.saveUser(jwtRequest.getUsername(), jwtRequest.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(">>>>>User saved successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(">>>>>Error: User not created!");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getSignIn() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable String username) {

        try {
            appUserSvc.userExists(username);
            return ResponseEntity.status(HttpStatus.OK).body(">>>>>Login successfully for: " + username);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(">>>>>Login unsuccessfully for: " + username);
        }
    }

}
