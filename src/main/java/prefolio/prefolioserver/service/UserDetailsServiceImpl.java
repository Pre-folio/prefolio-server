package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import prefolio.prefolioserver.domain.OAuth;
import prefolio.prefolioserver.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;

    // 이메일로 확인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) authRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다."));
    }

}

