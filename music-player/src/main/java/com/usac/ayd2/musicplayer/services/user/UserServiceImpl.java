package com.usac.ayd2.musicplayer.services.user;

import com.usac.ayd2.musicplayer.dao.user.UserDAO;
import com.usac.ayd2.musicplayer.dto.user.NewUserDTO;
import com.usac.ayd2.musicplayer.dto.user.UserDTO;
import com.usac.ayd2.musicplayer.mappers.user.UserMapper;
import com.usac.ayd2.musicplayer.models.user.User;
import com.usac.ayd2.musicplayer.utils.AlertUtil;
import com.usac.ayd2.musicplayer.utils.PasswordUtil;

import javafx.scene.control.Alert.AlertType;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private final UserDAO userDAO = new UserDAO();
    private final UserMapper userMapper = UserMapper.INSTANCE;

    private UserServiceImpl() { }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public UserDTO register(NewUserDTO newUserDTO) {
        if(userDAO.existsByUsername(newUserDTO.username())) {
            AlertUtil.showAlert(AlertType.ERROR, "El username ya esta en uso");
            return null;
        }
        final User user = userMapper.toUser(newUserDTO);
        final String password = PasswordUtil.hashPassword(newUserDTO.password());
        user.setPassword(password);
        userDAO.saveUser(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO login(NewUserDTO newUserDTO) {
        final User user = userDAO.findByUsername(newUserDTO.username());
        if (user == null) {
            AlertUtil.showAlert(AlertType.ERROR, "No existe un usuario con el username: " + newUserDTO.username());
            return null;
        }
        if (!PasswordUtil.verifyPassword(newUserDTO.password(), user.getPassword())) {
            AlertUtil.showAlert(AlertType.ERROR, "La contraseña es incorrecta");
            return null;
        }
        return userMapper.toUserDTO(user);
    }

}
