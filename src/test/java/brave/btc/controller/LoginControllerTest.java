package brave.btc.controller;

import brave.btc.domain.User;
import brave.btc.exception.auth.DuplicateLoginIdException;
import brave.btc.repository.UserRepository;
import brave.btc.service.UserService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName(value = "저장 테스트")
    public void registerTest() throws Exception {
        //given
        User user = new User("1","1","1","1");
        //when
        Long findId = userService.join(user);
        //then
        User user1 = userService.findUser(findId);
        Assertions.assertThat(user1).isEqualTo(user);
    }

    @Test
    @DisplayName(value = "아이디 중복체크 테스트")
    public void idDupTest() throws Exception {
        User user = new User("1","1","1","1");
        userService.join(user);
        assertThrows(DuplicateLoginIdException.class, ()->{
            userService.idDuplicateCheck("1");
        });
    }

}