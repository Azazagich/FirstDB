package org.example.service.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.User;
import org.example.service.dto.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserMapper implements Mapper<UserDTO, User>{

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public UserDTO toDTO(User user) {
        if (user != null) {
            LOGGER.debug("Converted from ageGroup to AgeGroupDTO: {}", user);
            return new UserDTO()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .email(user.getEmail())
                    .password(user.getPassword());
        }
        return new UserDTO();
    }

    @Override
    public Optional<UserDTO> toDTO(Optional<User> user) {
        if (user.isPresent()){
            LOGGER.debug("Converted from Optional<ageGroup> to Optional<AgeGroupDTO> {}:", user);
            return Optional.of(toDTO(user.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> toDTO(List<User> users) {
        if (!users.isEmpty()){
            LOGGER.debug("Converting list of AgeGroups to list of AgeGroupDTOs");
            return users.stream()
                    .filter(Objects::nonNull)
                    .map(this::toDTO)
                    .toList();
        }
        return List.of();    }

    @Override
    public User toEntity(UserDTO userDTO) {
        LOGGER.debug("Converted from AgeGroupDTO to AgeGroup: {}", userDTO);
        if (userDTO != null){
            return new User()
                    .id(userDTO.getId())
                    .firstName(userDTO.getFirstName())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword());
        }
        return new User();
    }

    @Override
    public List<User> toEntity(List<UserDTO> usersDTO) {
        if (!usersDTO.isEmpty()){
            LOGGER.debug("Converting list of UserDTO to list of User");
            return usersDTO.stream()
                    .filter(Objects::nonNull)
                    .map(this::toEntity)
                    .toList();
        }
        return List.of();
    }
}
