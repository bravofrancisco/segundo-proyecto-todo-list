package org.example.proyecto2aplicaciondegestiontarea.service;

import org.example.proyecto2aplicaciondegestiontarea.dto.RequestTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.dto.ResponseTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.exception.TaskNotFoundException;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumPriority;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumStatus;
import org.example.proyecto2aplicaciondegestiontarea.models.Task;
import org.example.proyecto2aplicaciondegestiontarea.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // =========================
    // Obtener todas las tareas
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<ResponseTasksDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ResponseTasksDTO> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(ResponseTasksDTO::fromEntity);
    }

    // =========================
    // Obtener tarea por ID
    // =========================
    @Override
    @Transactional(readOnly = true)
    public ResponseTasksDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return ResponseTasksDTO.fromEntity(task);
    }

    // =========================
    // Crear tarea
    // =========================
    @Override
    public ResponseTasksDTO save(RequestTasksDTO dto) {

        Task task = new Task(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getPriority(),
                dto.getDueDate() // ✅ CORREGIDO
        );

        Task savedTask = taskRepository.save(task);

        return ResponseTasksDTO.fromEntity(savedTask);
    }

    // =========================
    // Actualizar tarea
    // =========================
    @Override
    public ResponseTasksDTO update(Long id, RequestTasksDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->new TaskNotFoundException(id));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate()); // ✅ CORREGIDO

        Task updatedTask = taskRepository.save(task);

        return ResponseTasksDTO.fromEntity(updatedTask);
    }

    // =========================
    // Eliminar tarea
    // =========================
    @Override
    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    // Filtro por estado
    @Override
    public List<ResponseTasksDTO> findByStatus(EnumStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseTasksDTO> findByPriority(EnumPriority enumPriority) {
        return taskRepository.findByPriority(enumPriority)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseTasksDTO> findByStatusPriority(EnumStatus status, EnumPriority priority) {
        return taskRepository.findByStatusAndPriority(status,priority)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseTasksDTO> findByDueDateRange(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByDueDateBetween(start, end)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseTasksDTO> findOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDateTime.now(), EnumStatus.COMPLETED)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseTasksDTO> searchByText(String text) {
        return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text,text)
                .stream()
                .map(ResponseTasksDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ResponseTasksDTO>findAllPageable(Pageable pageable){
        return taskRepository.findAllBy(pageable)
                .map(ResponseTasksDTO::fromEntity);
    }
}
