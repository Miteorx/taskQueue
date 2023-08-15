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
              .id("USER")
              .privileges(List.of("GET_TASK"))
              .build(),
          Role.builder()
              .id("ADMIN")
              .privileges(List.of("CREATE_TASK"))
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
