package org.example.proyecto2aplicaciondegestiontarea.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.proyecto2aplicaciondegestiontarea.dto.RequestTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.dto.ResponseTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumPriority;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumStatus;
import org.example.proyecto2aplicaciondegestiontarea.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // =========================
    // Obtener todas las tareas
    // =========================
    @GetMapping
    public ResponseEntity<List<ResponseTasksDTO>> findAll() {
        List<ResponseTasksDTO> list = taskService.findAll();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // =========================
    // Obtener tarea por ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTasksDTO> findById(
            @PathVariable
            @Positive(message = "El id debe ser mayor a 0")
            Long id
    ) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    // =========================
    // Crear tarea
    // =========================
    @PostMapping
    public ResponseEntity<ResponseTasksDTO> create(
            @Valid @RequestBody RequestTasksDTO dto
    ) {
        ResponseTasksDTO response = taskService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================
    // Actualizar tarea
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTasksDTO> update(
            @PathVariable
            @Positive(message = "El id debe ser mayor a 0")
            Long id,
            @Valid @RequestBody RequestTasksDTO dto
    ) {
        return ResponseEntity.ok(taskService.update(id, dto));
    }

    // =========================
    // Eliminar tarea
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Positive(message = "El id debe ser mayor a 0")
            Long id
    ) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //filtro
    @GetMapping("/status")
    public ResponseEntity<List<ResponseTasksDTO>>filterByStatus(@RequestParam EnumStatus status){
        List<ResponseTasksDTO>listx =taskService.findByStatus(status);

        if (listx.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(listx);
    }

    @GetMapping("/priority")
    public ResponseEntity<List<ResponseTasksDTO>>filterByPriority(@RequestParam EnumPriority enumPriority){
        List<ResponseTasksDTO>priority = taskService.findByPriority(enumPriority);

        if (priority.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(priority);
    }

    @GetMapping("/statusandpriority")
    public ResponseEntity<List<ResponseTasksDTO>>findStatusAndPriority(
            @RequestParam(required = false) EnumStatus status,
            @RequestParam(required = false)EnumPriority priority
    ){
        List<ResponseTasksDTO>result;
        if (status != null && priority != null) {
            result = taskService.findByStatusPriority(status, priority);
        } else if (status != null) {
            result = taskService.findByStatus(status);
        } else if (priority != null) {
            result = taskService.findByPriority(priority);
        } else {
            result = taskService.findAll();
        }

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(result);
    }

    @GetMapping("/due-date")
    public ResponseEntity<List<ResponseTasksDTO>>findByDueDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end
    ){
        List<ResponseTasksDTO>result = taskService.findByDueDateRange(start, end);

        return  result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    //obtener tareas vencida
    @GetMapping("/overdue")
    public ResponseEntity<List<ResponseTasksDTO>>findOverDueTask(){
        List<ResponseTasksDTO> result = taskService.findOverdueTasks();

        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    //busca por texto
    @GetMapping("/search")
    public ResponseEntity<List<ResponseTasksDTO>>searchByText(
            @RequestParam String text)
    {
        List<ResponseTasksDTO>result = taskService.searchByText(text);

        return result.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ResponseTasksDTO>> findAllPage(Pageable pageable) {

        Page<ResponseTasksDTO> page = taskService.findAll(pageable);

        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(page);
    }
}
