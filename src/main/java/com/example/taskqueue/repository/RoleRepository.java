package com.example.taskqueue.repository;

import com.example.taskqueue.model.Role;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

  private static final Map<String, Role> ROLES = Stream.of(
          Role.builder()
              .id("ROLE_USER")
              .privileges(List.of())
              .build(),
          Role.builder()
              .id("ROLE_ADMIN")
              .privileges(List.of("USER_MANAGEMENT"))
              .build()
      )
      .collect(Collectors.toUnmodifiableMap(
          Role::getId,
          Function.identity())
      );

  public Optional<Role> getRole(String id) {
    return Optional.ofNullable(ROLES.get(id));
  }

}
