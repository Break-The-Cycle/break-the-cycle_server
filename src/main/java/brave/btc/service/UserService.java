package brave.btc.service;

import brave.btc.domain.User;
import brave.btc.dto.register.RegisterRequestDto;
import brave.btc.dto.register.RegisterResponseDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.DuplicateLoginIdException;
import brave.btc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    //회원 가입
    @Transactional
    public Long join(User User) {
//        validateDuplicateUser(User);
        userRepository.save(User);
        return User.getId();
    }

    public void login(String loginId, String password) {
        User user = findUser(loginId);
        System.out.println("user = " + user);
        //아이디 or 비밀번호 오류
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationInvalidException();
        }
    }

    public void idDuplicateCheck(String loginId) {
        if (findUser(loginId) != null) {
            throw new DuplicateLoginIdException();
        }
    }

    @Transactional
    public ResponseEntity<RegisterResponseDto> registerCheck(RegisterRequestDto request) {
        RegisterResponseDto response = new RegisterResponseDto(false, false, false, false, false, false);



        //비밀번호 매칭 확인
        if (!request.getPassword().equals(request.getPassword2())) {
            response.setPwdMatchError(true);
        }

        if(!response.isIdDupError() && !response.isPwdError()){
            User user = new User(request.getName(), request.getPhoneNumber(), request.getLoginId(), request.getPassword());
            join(user);
            log.info("회원 가입 완료");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        log.info("회원 가입 실패!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

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

    public User findUser(String loginId) {
        return userRepository.findByUserId(loginId);
    }
    
}
