package auth_server.service.imp;

import auth_server.enums.AuthError;
import auth_server.repository.EcUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailImp implements UserDetailsService {

  private final EcUserRepository ecUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return ecUserRepository.findByEmail(username)
        .map(user -> new User(user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority(user.getUserRole().getRoleDescription()))))
        .orElseThrow(() -> new UsernameNotFoundException(AuthError.AUTH_ERROR_1.getDescription()));
  }
}
