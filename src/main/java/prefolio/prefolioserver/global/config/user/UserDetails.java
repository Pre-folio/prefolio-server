package prefolio.prefolioserver.global.config.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String email;
    private User user;

    @Builder
    public UserDetails(String email) {
        this.email = email;
    }

    @Builder
    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //add some

        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
