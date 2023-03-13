package org.mycompany.user.web.controllers;

import jakarta.validation.Valid;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserLoginDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.mycompany.user.service.api.IUserAuthenticationService;
import org.mycompany.user.service.api.IUserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class  UserAuthenticationController {

    private IUserAuthenticationService userAuthenticationService;
    private IUserDataService userDataService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService,
                                        IUserDataService userDataService) {

        this.userAuthenticationService = userAuthenticationService;
        this.userDataService = userDataService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO userRegistration) {

        this.userAuthenticationService.register(userRegistration);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("/verification")
    public ResponseEntity<String> verifyCode(@RequestParam String code,
                                             @RequestParam String mail) {

        this.userAuthenticationService.verify(code, mail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLogin) {

        if (!this.userDataService.isActivated(userLogin.getMail())) {
            throw new BadCredentialsException(userLogin.getMail()
                    + " mail address has not been verified!");
        }
        String jwtToken = this.userAuthenticationService.login(userLogin);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jwtToken);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyData() {

        UserDTO userData = this.userAuthenticationService.getMyData();
        return ResponseEntity.ok(userData);
    }
}