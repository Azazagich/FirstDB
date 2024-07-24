package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.RoleCrudRepository;
import org.example.repository.UserCrudRepository;
import org.example.service.dto.RoleDTO;
import org.example.service.mapper.RoleMapper;

import java.util.List;
import java.util.Optional;

public class RoleService implements CrudService<RoleDTO>{

    private final RoleCrudRepository roleRepository;

    private final RoleMapper roleMapper;

    private final static Logger LOGGER = LogManager.getLogger();

    public RoleService(){
        this(new RoleCrudRepository(), new RoleMapper());
    }

    public RoleService(RoleCrudRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        LOGGER.debug("Saving RoleDTO: {}", roleDTO);
        return roleMapper.toDTO(roleRepository.save(roleMapper.toEntity(roleDTO)));

    }

    @Override
    public List<RoleDTO> saveAll(List<RoleDTO> rolesDTO) {
        LOGGER.debug("Saving all RolesDTO");
        return roleMapper.toDTO(roleRepository.saveAll(roleMapper.toEntity(rolesDTO)));    }

    @Override
    public Optional<RoleDTO> findById(Integer id) {
        LOGGER.debug("Find RoleDTO by id {}", id);
        return roleMapper.toDTO(roleRepository.findById(id));
    }

    @Override
    public List<RoleDTO> findAll() {
        LOGGER.debug("Find All RolesDTO elements");
        return roleMapper.toDTO(roleRepository.findAll());
    }

    @Override
    public boolean existById(Integer id) {
        LOGGER.debug("Checking existence of Roles by ID: {}", id);
        return roleRepository.existById(id);
    }

    @Override
    public boolean updateId(Integer id, RoleDTO nwRoleDTO) {
        LOGGER.debug("Updating Role with ID: {}", id);
        if (id != null && nwRoleDTO != null){
            return roleRepository.updateId(id, roleMapper.toEntity(nwRoleDTO));
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("Deleting Role by ID: {}", id);
        roleRepository.deleteById(id);
    }

    @Override
    public void delete(RoleDTO roleDTO) {
        LOGGER.debug("Deleting Role: {}", roleDTO);
        roleRepository.delete(roleMapper.toEntity(roleDTO));
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Deleting all Roles");
        roleRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<RoleDTO> rolesDTO) {
        LOGGER.debug("Deleting all Roles list");
        roleRepository.deleteAll(roleMapper.toEntity(rolesDTO));
    }
}
