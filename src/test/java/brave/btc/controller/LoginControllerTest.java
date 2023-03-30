package brave.btc.controller;

import brave.btc.domain.User;
import brave.btc.repository.UserRepository;
import brave.btc.service.UserService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.spec.EdDSAParameterSpec;

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
    public void nullTest() throws Exception {
        Assertions.assertThat(userService.findUser(1L)).isNull();
    }


}