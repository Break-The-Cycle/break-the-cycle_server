package brave.btc.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.constant.enums.UserType;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.domain.common.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(UsePerson usePerson) {
        this.user = User.builder()
            .id(usePerson.getId())
            .loginId(usePerson.getLoginId())
            .password(usePerson.getPassword())
            .phoneNumber(usePerson.getPhoneNumber())
            .userType(UserType.USE_PERSON)
            .isAccountNonExpired(usePerson.getIsAccountNonExpired())
            .isCredentialsNonExpired(usePerson.getIsCredentialsNonExpired())
            .isAccountNonLocked(usePerson.getIsAccountNonLocked())
            .isEnabled(usePerson.getIsEnabled())
            .build();
    }

    public PrincipalDetails(ManagePerson managePerson) {

        UserType userType = managePerson.getDivision() == ManageDivision.BACKOFFICE_MANAGE_PERSON ?
            UserType.ADMIN :
            UserType.MANAGE_PERSON;

        this.user = User.builder()
            .id(managePerson.getId())
            .loginId(managePerson.getLoginId())
            .password(managePerson.getPassword())
            .phoneNumber(managePerson.getPhoneNumber())
            .userType(userType)
            .isAccountNonExpired(managePerson.getIsAccountNonExpired())
            .isCredentialsNonExpired(managePerson.getIsCredentialsNonExpired())
            .isAccountNonLocked(managePerson.getIsAccountNonLocked())
            .isEnabled(managePerson.getIsEnabled())
            .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        UserType userType = user.getUserType();
        log.debug("[getAuthorities] userType: {}", userType);
        if (userType == UserType.USE_PERSON) {
            authorities.add((GrantedAuthority)() -> "ROLE_USE_PERSON");
        } else if (userType == UserType.MANAGE_PERSON) {
            authorities.add((GrantedAuthority)() -> "ROLE_USE_PERSON");
            authorities.add((GrantedAuthority)() -> "ROLE_MANAGER");
        } else if (userType == UserType.ADMIN) {
            authorities.add((GrantedAuthority)() -> "ROLE_USE_PERSON");
            authorities.add((GrantedAuthority)() -> "ROLE_MANAGER");
            authorities.add((GrantedAuthority)() -> "ROLE_ADMIN");
        } else {
            throw new IllegalStateException("비정상 상태입니다.");
        }
        log.debug("[getAuthorities] authorities: {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

}
