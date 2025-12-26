package org.example.proyecto2aplicaciondegestiontarea.service;
import org.example.proyecto2aplicaciondegestiontarea.dto.RequestTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.dto.ResponseTasksDTO;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumPriority;
import org.example.proyecto2aplicaciondegestiontarea.models.Enum.EnumStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    List<ResponseTasksDTO> findAll();

    @Transactional(readOnly = true)
    Page<ResponseTasksDTO> findAll(Pageable pageable);

    ResponseTasksDTO findById(Long id);

    ResponseTasksDTO save(RequestTasksDTO requestTasksDTO);

    ResponseTasksDTO update(Long id, RequestTasksDTO requestTasksDTO);

    void delete(Long id);

    List<ResponseTasksDTO>findByStatus(EnumStatus status);
    List<ResponseTasksDTO>findByPriority(EnumPriority enumPriority);
    List<ResponseTasksDTO>findByStatusPriority(
            EnumStatus status, EnumPriority priority
    );

    List<ResponseTasksDTO>findByDueDateRange(LocalDateTime start, LocalDateTime end);

    //buscar por fecha vencida
    List<ResponseTasksDTO>findOverdueTasks();

    //busqueda por texto
    List<ResponseTasksDTO>searchByText(String text);


    // 👇 ESTE es el nuevo método correcto
    Page<ResponseTasksDTO> findAllPageable(Pageable pageable);
}
