package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.RoleCrudRepository;
import org.example.repository.UserCrudRepository;
import org.example.service.dto.UserDTO;
import org.example.service.mapper.RoleMapper;
import org.example.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

public class UserService implements CrudService<UserDTO>{

    private final UserCrudRepository userRepository;

    private final UserMapper userMapper;

    private final static Logger LOGGER = LogManager.getLogger();

    public UserService(){
        this(new UserCrudRepository(), new UserMapper());
    }

    public UserService(UserCrudRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDTO save(UserDTO userDTO) {
        LOGGER.debug("Saving UserDTO: {}", userDTO);
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public List<UserDTO> saveAll(List<UserDTO> usersDTO) {
        LOGGER.debug("Saving all UsersDTO");
        return userMapper.toDTO(userRepository.saveAll(userMapper.toEntity(usersDTO)));
    }

    @Override
    public Optional<UserDTO> findById(Integer id) {
        LOGGER.debug("Find UserDTO by id {}", id);
        return userMapper.toDTO(userRepository.findById(id));
    }

    @Override
    public List<UserDTO> findAll() {
        LOGGER.debug("Find All UsersDTO elements");
        return userMapper.toDTO(userRepository.findAll());
    }

    @Override
    public boolean existById(Integer id) {
        LOGGER.debug("Checking existence of User by ID: {}", id);
        return userRepository.existById(id);
    }

    @Override
    public boolean updateId(Integer id, UserDTO nwUserDTO) {
        LOGGER.debug("Updating User with ID: {}", id);
        if (id != null && nwUserDTO != null){
            return userRepository.updateId(id, userMapper.toEntity(nwUserDTO));
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("Deleting User by ID: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public void delete(UserDTO userDTO) {
        LOGGER.debug("Deleting User: {}", userDTO);
        userRepository.delete(userMapper.toEntity(userDTO));
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Deleting all Users");
        userRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<UserDTO> usersDTO) {
        LOGGER.debug("Deleting all Users list");
        userRepository.deleteAll(userMapper.toEntity(usersDTO));
    }
}
