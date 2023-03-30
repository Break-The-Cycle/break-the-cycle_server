package brave.btc.service;

import brave.btc.domain.User;
import brave.btc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원 가입
    @Transactional
    public Long join(User User) {
//        validateDuplicateUser(User);
        userRepository.save(User);
        return User.getId();
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

    public User findUser(String login_id) {
        return userRepository.findByUserId(login_id);
    }
    
}
