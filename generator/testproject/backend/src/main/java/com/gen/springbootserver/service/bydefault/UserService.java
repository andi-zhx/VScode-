package com.gen.springbootserver.service.bydefault;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.gen.springbootserver.config.GgProperties;
import com.gen.springbootserver.dto.authentication.SignUpDTO;
import com.gen.springbootserver.dto.authentication.Tokens;
import com.gen.springbootserver.dto.bydefault.AddressDTO;
import com.gen.springbootserver.dto.bydefault.GridData;
import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.dto.errors.CommonHttpException;
import com.gen.springbootserver.dto.filter.bydefault.*;
import com.gen.springbootserver.dto.user.ChangePasswordRequest;
import com.gen.springbootserver.dto.user.UserContextHolder;
import com.gen.springbootserver.dto.user.UserDTO;
import com.gen.springbootserver.mybatis.dao.UserMapper;
import com.gen.springbootserver.mybatis.model.Menu;
import com.gen.springbootserver.mybatis.model.User;
import com.gen.springbootserver.mybatis.model.UserExample;
import com.gen.springbootserver.mybatis.model.UserExample.Criteria;
import com.gen.springbootserver.utils.ServiceUtils;

@Service
// @SuppressWarnings({ "checkstyle:ParameterNumber" })
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    SettingsService settingsService;
    @Autowired
    ImageService imageService;
    @Autowired
    private ModelMapper modelMapper;
    private String defaultImage;

    @Autowired
    private TokenService tokenService;
    public static final Integer DEFAULT_AGE = 18;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(GgProperties properties) {
        defaultImage = properties.getUser().getDefaultImage();
    }

    // #region gg
    @Transactional
    public User create(User testgen) throws CommonApiException {
        userMapper.insert(testgen);
        try {
            testgen = get(testgen.getId());
        } catch (Exception e) {
        }
        return testgen;
    }

    @Transactional
    public User update(Long id, User testgen) throws CommonApiException {
        User exist = userMapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new CommonApiException("this User not exixt");
        }
        userMapper.updateByPrimaryKey(testgen);
        return get(testgen.getId());
    }

    @Transactional
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User get(Long id) throws CommonApiException {
        User testgen = userMapper.selectByPrimaryKey(id);
        if (testgen == null) {
            throw new CommonApiException("this Testgen not exixt");
        }
        return testgen;
    }

    public List<User> getList(UserExample example) {
        return userMapper.selectByExample(example);
    }

    public Long getListCount(UserExample example) {
        return userMapper.countByExample(example);
    }

    public List<Menu> getuserMenus(Long id) {
        return userMapper.getuserMenus(id);
    }
    // #endregion

    public UserDTO getCurrentUser() {
        User user = UserContextHolder.getUser();
        user.setSettings(settingsService.getSettingBySettingsId(user.getSettingsId()));
        return modelMapper.map(user, UserDTO.class);
    }

    public Tokens updateCurrentUser(UserDTO userDTO) {
        try {
            User user = UserContextHolder.getUser();
            Long id = user.getId();
            UserDTO updatedUser = updateUser(id, userDTO);
            user = modelMapper.map(updatedUser, User.class);
            return tokenService.createToken(user);
        } catch (CommonApiException exception) {
            throw new CommonHttpException("Email is invalid or already taken", HttpStatus.CONFLICT);
        }
    }

    public User findByEmail(String email) {
        List<User> result = getUserbyEmail(email);
        if (result.size() == 1) {
            User user = result.get(0);
            return user;
        } else {
            throw new CommonHttpException("User with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public UserDTO getUserDtoById(Long id) {
        User existingUser = userMapper.selectByPrimaryKey(id);
        if (existingUser != null) {
            return modelMapper.map(existingUser, UserDTO.class);
        } else {
            throw new CommonHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }

    }

    public Long getSettingsIdByUserId(Long userid) {
        User user = userMapper.selectByPrimaryKey(userid);
        return user.getSettingsId();
    }

    public User getUserbyUserid(Long userid) {
        User existingUser = userMapper.selectByPrimaryKey(userid);
        if (existingUser != null) {
            return existingUser;
        } else {
            throw new CommonHttpException("User with id: " + userid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public User register(SignUpDTO signUpDTO) throws CommonApiException {
        if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
            throw new CommonHttpException("Passwords don't match", HttpStatus.BAD_REQUEST);
        }
        String email = signUpDTO.getEmail();
        if (getUserbyEmail(email).size() > 0) {
            throw new CommonApiException("User with email: " + email + " already registered");
        }
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setLogin(signUpDTO.getFullName());
        String encodedPassword = encodePassword(signUpDTO.getPassword());
        user.setPasswordHash(encodedPassword);
        user.setAge(DEFAULT_AGE);

        // In current version settings、 role are default
        user.setCreatedAt(LocalDateTime.now(ZoneId.systemDefault()));
        user.setSettingsId(1L);

        userMapper.insert(user);
        imageService.updateUserImageById(user.getId(), defaultImage);
        roleService.setDefaultRoleByuserId(user.getId());
        return userMapper.selectByPrimaryKey(user.getId());
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);

            String email = user.getEmail();
            if (getUserbyEmail(email).size() > 0) {
                throw new CommonApiException("User with email: " + email + " already registered");
            }
            // In current version password 、settings、 role are default
            user.setPasswordHash(encodePassword("123456"));
            user.setCreatedAt(LocalDateTime.now(ZoneId.systemDefault()));
            user.setSettingsId(1L);
            userMapper.insert(user);
            imageService.updateUserImageById(user.getId(), defaultImage);
            userDTO.setImageBase64(defaultImage);
            roleService.setDefaultRoleByuserId(user.getId());
            user = userMapper.selectByPrimaryKey(user.getId());
            return modelMapper.map(user, UserDTO.class);
        } catch (CommonApiException exception) {
            throw new CommonHttpException("Email is invalid or already taken", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public void updateUserSettingid(Long userid, Long settingsId) {
        User user = userMapper.selectByPrimaryKey(userid);
        user.setSettingsId(settingsId);
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = changePasswordRequest.getUser();
        String encodedPassword = encodePassword(changePasswordRequest.getPassword());
        user.setPasswordHash(encodedPassword);
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional
    public UserDTO updateUserById(Long userId, UserDTO userDTO) {
        try {
            return updateUser(userId, userDTO);
        } catch (CommonApiException exception) {
            throw new CommonHttpException("Email is invalid or already taken", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    private UserDTO updateUser(Long id, UserDTO userDTO) throws CommonApiException {
        User existingUser = getUserbyUserid(id);
        User updatedUser = modelMapper.map(userDTO, User.class);
        String updatedUserEmail = updatedUser.getEmail();
        if (!existingUser.getEmail().equals(updatedUserEmail) && getUserbyEmail(updatedUserEmail).size() > 0)
            throw new CommonApiException("User with email: " + updatedUserEmail + " already registered");
        updatedUser.setId(id);
        updatedUser.setPasswordHash(existingUser.getPasswordHash());
        updatedUser.setUpdatedAt(LocalDateTime.now(ZoneId.systemDefault()));
        // Current version doesn't update roles
        updatedUser.setRoles(existingUser.getRoles());
        updatedUser.setImage(existingUser.getImage());
        userMapper.updateByPrimaryKey(updatedUser);
        // update user settings
        settingsService.updateSettingsByUserId(id, userDTO.getSettings().getThemeName());
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public boolean deleteUserByuserid(Long id) {
        try {
            userMapper.deleteByPrimaryKey(id);
            roleService.deleteByuserid(id);
            imageService.del(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new CommonHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public GridData<UserDTO> getDataForGrid(UsersGridFilter filter) throws Exception {
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        HashMap<String, String> map = new HashMap<>();
        Field[] fileds = filter.getClass().getDeclaredFields();
        for (int i = 0; i < fileds.length; i++) {
            String Columnname = ServiceUtils.dealFilterfileName("UserMapper", fileds[i].getName());
            Object ob = ServiceUtils.getGetMethod(filter, fileds[i].getName());
            if (ob != null) {
                map.put(Columnname, ob.toString());
            }
        }
        criteria.andMultiseriateOrLike(map);

        example.setOrderByClause(ServiceUtils.dealsortString("UserMapper", filter.getSortBy(), filter.getOrderBy()));

        long totalCount = userMapper.countByExample(example);

        example.setlimitStart(ServiceUtils.getlimitStart(filter.getPageNumber(), filter.getPageSize()));
        example.setlimitEnd(ServiceUtils.getlimitEnd(filter.getPageSize()));
        List<User> result = userMapper.selectByExample(example);
        return parsePageToGridData(result, totalCount);
    }

    private GridData<UserDTO> parsePageToGridData(List<User> orderList, Long totalCount) {
        GridData<UserDTO> gridData = new GridData<>();
        gridData.setItems(mapOrdersToOrderDTO(orderList));
        gridData.setTotalCount(totalCount);
        return gridData;
    }

    private List<UserDTO> mapOrdersToOrderDTO(List<User> orders) {
        return orders.stream().map(order -> {
            UserDTO dto = modelMapper.map(order, UserDTO.class);
            if (dto.getAddress() == null) {
                dto.setAddress(new AddressDTO());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    private List<User> getUserbyEmail(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        return userMapper.selectByExample(example);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}