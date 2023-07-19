package prefolio.prefolioserver.global.config.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.exception.UserNotFound;
import prefolio.prefolioserver.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        return userRepository.findById(Long.parseLong(id))
                .map(user -> createUser(Long.parseLong(id), user))
                .orElseThrow(() -> UserNotFound.EXCEPTION);
    }

    @Transactional
    public UserDetails loadTempUserByUsername(Long id) throws UsernameNotFoundException {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> UserNotFound.EXCEPTION);

        return UserDetails.builder()
                .email(findUser.getEmail())
                .build();
    }

    private UserDetails createUser(Long id, User user) {
        return new UserDetails(user);
    }
}

