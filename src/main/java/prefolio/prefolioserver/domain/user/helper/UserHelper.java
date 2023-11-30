package prefolio.prefolioserver.domain.user.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.user.exception.UserNotFound;
import prefolio.prefolioserver.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public boolean duplicatedNickname(String username) {
        return userRepository.findByNickname(username).isPresent();
    }

    public User findUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> {
                            throw UserNotFound.EXCEPTION;
                        });
    }
}
