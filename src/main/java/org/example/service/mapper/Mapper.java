package org.example.service.mapper;

import java.util.List;
import java.util.Optional;

public interface Mapper<DTO, Entity> {

    /**
     * Converts an entity to its corresponding DTO.
     *
     * @param entity the entity to convert
     * @return the corresponding DTO
     */
    DTO toDTO(Entity entity);

    /**
     * Converts an Optional entity to an Optional DTO.
     *
     * @param entity the Optional entity to convert
     * @return an Optional containing the corresponding DTO if the entity is present, otherwise an empty Optional
     */
    Optional<DTO> toDTO(Optional<Entity> entity);

    /**
     * Converts a list of entities to a list of corresponding DTOs.
     *
     * @param entities the list of entities to convert
     * @return a list of corresponding DTOs
     */
    List<DTO> toDTO(List<Entity> entities);

    /**
     * Converts a DTO to its corresponding entity.
     *
     * @param dto the DTO to convert
     * @return the corresponding entity
     */
    Entity toEntity(DTO dto);

    /**
     * Converts a list of DTOs to a list of corresponding entities.
     *
     * @param DTOs the list of DTOs to convert
     * @return a list of corresponding entities
     */
    List<Entity> toEntity(List<DTO> DTOs);
}
