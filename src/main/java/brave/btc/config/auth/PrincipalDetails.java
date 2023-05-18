package brave.btc.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import brave.btc.constant.enums.UserType;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.domain.common.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            .build();
    }

    public PrincipalDetails(ManagePerson managePerson) {
        this.user = User.builder()
            .id(managePerson.getId())
            .loginId(managePerson.getLoginId())
            .password(managePerson.getPassword())
            .phoneNumber(managePerson.getPhoneNumber())
            .userType(UserType.MANAGE_PERSON)
            .build();
    }

    //TODO : ADMIN 만들어서 수정하기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        UserType userType = user.getUserType();
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
