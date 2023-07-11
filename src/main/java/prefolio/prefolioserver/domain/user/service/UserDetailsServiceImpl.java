package prefolio.prefolioserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.global.error.CustomException;
import prefolio.prefolioserver.domain.user.repository.UserRepository;

import static prefolio.prefolioserver.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 이메일로 확인
    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String id) throws UsernameNotFoundException {
        User findUser = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserDetailsImpl.builder()
                .email(findUser.getEmail())
                .build();

    }

}

