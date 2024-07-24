package org.example.service.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Role;
import org.example.service.dto.RoleDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RoleMapper implements Mapper<RoleDTO, Role> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RoleDTO toDTO(Role role) {
        if (role != null){
            LOGGER.debug("Converted from Role to RoleDTO: {}", role);
            return new RoleDTO()
                    .id(role.getId())
                    .name(role.getName());
        }
        return new RoleDTO();
    }

    @Override
    public Optional<RoleDTO> toDTO(Optional<Role> role) {
        if (role.isPresent()){
            LOGGER.debug("Converted from Optional<Role> to Optional<RoleDTO>: {}", role);
            return Optional.of(toDTO(role.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<RoleDTO> toDTO(List<Role> roles) {
        if (!roles.isEmpty()){
            LOGGER.debug("Converting list of Role to list of RolesDTO");
            return roles.stream()
                    .filter(Objects::nonNull)
                    .map(this::toDTO)
                    .toList();
        }
        return List.of();
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        LOGGER.debug("Converted from RolesDTO to Role: {}", roleDTO);
        if (roleDTO != null){
            return new Role()
                    .id(roleDTO.getId())
                    .name(roleDTO.getName());
        }
        return new Role();
    }

    @Override
    public List<Role> toEntity(List<RoleDTO> rolesDTO) {
        if (!rolesDTO.isEmpty()){
            LOGGER.debug("Converting list of RoleDTO to list of Role");
            return rolesDTO.stream()
                    .filter(Objects::nonNull)
                    .map(this::toEntity)
                    .toList();
        }
        return List.of();
    }
}
