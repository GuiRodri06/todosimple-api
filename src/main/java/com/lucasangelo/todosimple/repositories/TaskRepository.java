package com.lucasangelo.todosimple.repositories;

import com.lucasangelo.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Id(Long id);

    // @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    // List<Task> FindByUser_Id(@Param("id") Long id);

    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> FindByUser_Id(@Param("id") Long id);

}
