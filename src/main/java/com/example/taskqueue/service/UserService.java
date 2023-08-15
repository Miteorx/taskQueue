package com.example.taskqueue.service;

import com.example.taskqueue.model.Role;
import com.example.taskqueue.model.User;
import com.example.taskqueue.repository.RoleRepository;
import com.example.taskqueue.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  PasswordEncoder passwordEncoder = PasswordEncoderFactories
      .createDelegatingPasswordEncoder();

  @PostConstruct
  public void init() {
    Optional<User> admin = userRepository.findByUser("admin");
    if (admin.isEmpty()) {
      userRepository.save(User.builder()
          .user("admin")
          .password(passwordEncoder.encode("admin"))
          .role("ADMIN")
          .enabled(true)
          .build()
      );
    }

    Optional<User> user = userRepository.findByUser("user");
    if (user.isEmpty()) {
      userRepository.save(User.builder()
          .user("user")
          .password(passwordEncoder.encode("user"))
          .role("USER")
          .enabled(true)
          .build()
      );
    }

    Optional<User> secondUser = userRepository.findByUser("user2");
    if (secondUser.isEmpty()) {
      userRepository.save(User.builder()
          .user("user2")
          .password(passwordEncoder.encode("user2"))
          .role("USER")
          .enabled(true)
          .build()
      );
    }
  }

  public User getUserByUsername(String username) {
    Optional<User> user = userRepository.findByUser(username);
    if (user.isPresent()) {
      return user.get();
    } else throw new UsernameNotFoundException("User not found");
  }

  public List<User> getAllWorker() {
    return userRepository.findUsersByRole("USER");
  }


  @Override
  public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
    return userRepository.findByUser(user)
        .map(this::toUserDetails)
        .orElseThrow(() ->
            new UsernameNotFoundException("User with name '%s' not found".formatted(user)));
  }

  private UserDetails toUserDetails(User user) {
    return org.springframework.security.core.userdetails.User.withUsername(user.getUser())
        .password(user.getPassword())
        .authorities(collectAuthorities(user.getRole()))
        .disabled(!user.isEnabled())
        .build();
  }

  private List<GrantedAuthority> collectAuthorities(String role) {
    return roleRepository.getRole(role)
        .map(Role::getPrivileges)
        .stream().flatMap(Collection::stream)
        .map(priv -> (GrantedAuthority) new SimpleGrantedAuthority("PRIV_" + priv))
        .toList();
  }
}
