package brave.btc.service;

import brave.btc.domain.User;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.auth.register.RegisterRequestDto;
import brave.btc.dto.auth.register.RegisterResponseDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    //회원 가입

    public Long join(User User) {
//        validateDuplicateUser(User);
        userRepository.save(User);
        return User.getId();
    }

    public void login(String loginId, String password) {
        // User user = findUser(loginId);
        User user=null;
        System.out.println("user = " + user);
        //아이디 or 비밀번호 오류
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationInvalidException();
        }
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId) {
        User user = userRepository.findByUserId(loginId)
            .orElse(null);

        if (user == null) {
            return CommonResponseDto.builder()
                .message("사용 가능한 아이디입니다.")
                .build();
        }
        return CommonResponseDto.builder()
            .message("이미 사용중인 아이디입니다.")
            .build();
    }


    public CommonResponseDto<Object> register(RegisterRequestDto request) {

        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        if (password.equals(password2)) {
            //TODO : MapStruct 도입하기 ... (나중에 필드 많아지면 훨씬 편함) DTO <-> Entity Mapper
            User newUser = User.builder()
                .loginId(request.getLoginId())
                .password(password)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
            userRepository.save(newUser);
            log.info("[register] 회원 가입 완료");
            return CommonResponseDto.builder()
                .message("회원 가입이 완료되었습니다.")
                .build();
        }
        log.error("[register] 회원 가입 실패");
        throw new AuthenticationInvalidException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
    }

//    private void validateDuplicateUser(User User) {
//        List<User> findUsers = userRepository.findByName(User.getName());
//        if (!findUsers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
    //회원 전체 조회

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findOne(id);
    }

    
}
