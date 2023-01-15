package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    // 이메일로 확인
    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        OAuth findUser = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if(findUser != null) {
            return UserDetailsImpl.builder()
                    .email(findUser.getEmail())
                    .isMember(findUser.getIsMember())
                    .build();
        }
        return null;
    }

}

