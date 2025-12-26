package org.example.proyecto2aplicaciondegestiontarea.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumPriority;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumStatus;
import org.example.proyecto2aplicaciondegestiontarea.models.Task;

import java.time.LocalDateTime;

public class ResponseTasksDTO {

    private Long id;
    private String title;
    private String description;
    private EnumStatus status;
    private EnumPriority priority;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public ResponseTasksDTO() {
    }

    public ResponseTasksDTO(Long id,
                            String title,
                            String description,
                            EnumStatus status,
                            EnumPriority priority,
                            LocalDateTime dueDate,
                            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    // =========================
    // Mapper Entity → DTO
    // =========================
    public static ResponseTasksDTO fromEntity(Task task) {
        return new ResponseTasksDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt()
        );
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EnumStatus getStatus() { return status; }
    public void setStatus(EnumStatus status) { this.status = status; }

    public EnumPriority getPriority() { return priority; }
    public void setPriority(EnumPriority priority) { this.priority = priority; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
