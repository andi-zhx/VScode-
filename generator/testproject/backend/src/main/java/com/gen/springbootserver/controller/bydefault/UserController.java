package com.gen.springbootserver.controller.bydefault;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.gen.springbootserver.dto.authentication.Tokens;
import com.gen.springbootserver.dto.bydefault.GridData;
import com.gen.springbootserver.dto.bydefault.ResponseMessage;
import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.dto.filter.bydefault.*;
import com.gen.springbootserver.dto.user.UserDTO;
import com.gen.springbootserver.mybatis.model.Image;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.mybatis.model.User;
import com.gen.springbootserver.service.bydefault.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @GetMapping("")
    public ResponseEntity<GridData<UserDTO>> getDataForGrid(UsersGridFilter usersGridFilter) throws Exception {
        usersGridFilter = usersGridFilter == null ? new UsersGridFilter() : usersGridFilter;
        return ok(userService.getDataForGrid(usersGridFilter));
    }

    /**
     * Get current user
     * 
     * @return current user data
     */
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ok(userService.getCurrentUser());
    }

    /**
     * Update current user
     * 
     * @param userDTO updated user data
     * @return updated user data
     */
    @PutMapping("/current")
    public ResponseEntity<Tokens> updateCurrentUser(@Valid @RequestBody UserDTO userDTO) {
        return ok(userService.updateCurrentUser(userDTO));
    }

    /**
     * Get user. Allowed only for Admin
     * 
     * @param id user id
     * @return user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ok(userService.getUserDtoById(id));
    }

    /**
     * Update user. Allowed only for Admin
     * 
     * @param id      user id
     * @param userDTO updated user data
     * @return updated user data
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserById(id, userDTO);
        return ok(updatedUser);
    }

    /**
     * Create user. Allowed only for Admin
     * 
     * @param userDTO new user data
     * @return created user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ok(userService.createUser(userDTO));
    }

    /**
     * Update current user image
     *
     * @param baseString updated user image
     * @return updated image
     */
    @PutMapping("/{id}/photo")
    public ResponseEntity<Image> updateUserImage(@PathVariable Long id, @Valid @RequestBody String baseString) {
        Image image = imageService.updateUserImageById(id, baseString);
        return ok(image);
    }

    /**
     * Get user image by id
     *
     * @param id user image id
     * @return user image
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long id, @RequestParam String token) {
        Image image = imageService.getImageById(id, token);
        byte[] imageBytes = image.getImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    /**
     * Delete user
     * 
     * @param id user id
     * @return boolean result
     * @throws CommonApiException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(Authentication auth, @PathVariable Long id) throws CommonApiException {
        User curuser = ((GGUserDetailsService.GGUserDetails) auth.getPrincipal()).getUser();
        if (curuser.getId().equals(id)) {
            return new ResponseEntity<>("It is impossible to delete the current user", HttpStatus.BAD_REQUEST);
        }
        User u= userService.get(id);
        List<Role> uroles = u.getRoles().stream().filter((Role role) -> role.getName().equals("ADMIN"))
        .collect(Collectors.toList());
        if (!uroles.isEmpty()) {
            return new ResponseEntity<>("It is impossible to delete the admin", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUserByuserid(id);
        return ok(new ResponseMessage("OK"));
    }
}