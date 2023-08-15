package com.example.taskqueue.repository;

import com.example.taskqueue.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUser(String user);

  Optional<User> findUserById(Long id);

  List<User> findUsersByRole(String role);
}
