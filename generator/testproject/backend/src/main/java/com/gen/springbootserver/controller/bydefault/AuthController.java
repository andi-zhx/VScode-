package com.gen.springbootserver.controller.bydefault;

import javax.validation.Valid;

import com.gen.springbootserver.dto.authentication.LoginDTO;
import com.gen.springbootserver.dto.authentication.RefreshTokenDTO;
import com.gen.springbootserver.dto.authentication.SignUpDTO;
import com.gen.springbootserver.dto.authentication.Tokens;
import com.gen.springbootserver.dto.bydefault.ResponseMessage;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.resetpassword.RequestPasswordDTO;
import com.gen.springbootserver.dto.resetpassword.ResetPasswordDTO;
import com.gen.springbootserver.dto.resetpassword.RestorePasswordDTO;
import com.gen.springbootserver.service.bydefault.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    RestorePasswordService restorePasswordService;
    @Autowired
    RequestPasswordService requestPasswordService;
    @Autowired
    ResetPasswordService resetPasswordService;

    /**
     * Login user
     * 
     * @param loginDTO user credentials
     * @return generated token
     */
    @ApiOperation(value = "login", notes = "admin@admin.admin  !2e4S  user@user.user  12345")
    @PostMapping("/login")
    public ResponseEntity<RefreshTokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        Tokens tokens = authService.login(loginDTO);
        return toResponse(tokens);
    }

    /**
     * Restore password
     * 
     * @param restorePasswordDTO new password with token
     * @return result message
     */
    @PostMapping("/restore-pass")
    public ResponseEntity<ResponseMessage> restorePassword(@Valid @RequestBody RestorePasswordDTO restorePasswordDTO) {
        if (!restorePasswordDTO.getNewPassword().equals(restorePasswordDTO.getConfirmPassword())) {
            throw new CommonHttpException("Passwords don't match", HttpStatus.BAD_REQUEST);
        }
        restorePasswordService.restorePassword(restorePasswordDTO);
        return ok(new ResponseMessage("Password was restored"));
    }

    /**
     * Sign up
     * 
     * @param signUpDTO sign up user data
     * @return token
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RefreshTokenDTO> register(@Valid @RequestBody SignUpDTO signUpDTO) {
        if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
            throw new CommonHttpException("Passwords don't match", HttpStatus.BAD_REQUEST);
        }
        Tokens tokens = authService.register(signUpDTO);
        return toResponse(tokens);
    }

    /**
     * Request password. Generate link for restoring password which should be sent
     * via email
     * 
     * @param requestPasswordDTO object with user email
     * @return result message
     */
    @PostMapping("/request-pass")
    public ResponseEntity<ResponseMessage> requestPassword(@Valid @RequestBody RequestPasswordDTO requestPasswordDTO) {
        requestPasswordService.requestPassword(requestPasswordDTO);
        return ok(new ResponseMessage("Ok"));
    }

    /**
     * Sign out. Perform any required actions to log out user, like invalidate user
     * session. Implement your required logic
     * 
     * @return result message
     */
    @PostMapping("/sign-out")
    public ResponseEntity<ResponseMessage> logout() {
        return ok(new ResponseMessage("Ok"));
    }

    /**
     * Reset password for signed in user
     * 
     * @param resetPasswordDTO new and confirmed passwords
     * @return result message
     */
    @PostMapping("/reset-pass")
    public ResponseEntity<ResponseMessage> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        if (!resetPasswordDTO.getConfirmPassword().equals(resetPasswordDTO.getPassword())) {
            throw new CommonHttpException("Passwords don't match", HttpStatus.BAD_REQUEST);
        }

        resetPasswordService.resetPassword(resetPasswordDTO);
        return ok(new ResponseMessage("Password was reset"));
    }

    /**
     * Refresh token
     * 
     * @param refreshTokenDTO refresh token
     * @return new token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenDTO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        Tokens tokens = authService.refreshToken(refreshTokenDTO);
        return toResponse(tokens);
    }

    private ResponseEntity<RefreshTokenDTO> toResponse(Tokens tokens) {
        return ok(new RefreshTokenDTO(tokens));
    }

}