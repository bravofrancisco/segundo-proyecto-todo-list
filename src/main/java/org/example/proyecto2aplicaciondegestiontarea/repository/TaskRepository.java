package org.example.proyecto2aplicaciondegestiontarea.repository;

import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumPriority;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumStatus;
import org.example.proyecto2aplicaciondegestiontarea.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {

    List<Task>findByStatus(EnumStatus status);
    List<Task>findByPriority(EnumPriority enumPriority);
    List<Task> findByStatusAndPriority(EnumStatus status, EnumPriority priority);

    //tarea entre fechas
    List<Task>findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    //obtener tarea vencida
    List<Task>findByDueDateBeforeAndStatusNot(LocalDateTime star, EnumStatus end);

    //busqueda por titulo
    List<Task>findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    Page<Task> findAllBy(Pageable pageable);

}
