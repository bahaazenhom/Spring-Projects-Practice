package com.bahaa.todo.mapper;
import com.bahaa.todo.entity.Task;
import com.bahaa.todo.model.dto.TaskDto;
import com.bahaa.todo.model.dto.TaskDtoRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toTaskDto(Task task);
    Task toTaskEntity(TaskDto taskDto);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);
}
