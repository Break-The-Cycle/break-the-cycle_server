package brave.btc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import brave.btc.repository.app.UsePersonRepository;
import brave.btc.service.app.auth.AuthServiceImpl;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    AuthServiceImpl authServiceImpl;
    @Autowired
    UsePersonRepository usePersonRepository;

    // @Test
    // @DisplayName(value = "저장 테스트")
    // public void registerTest() throws Exception {
    //     //given
    //     User user = new User("1","1","1","1");
    //     //when
    //     Long findId = authService.join(user);
    //     //then
    //     User user1 = authService.findUser(findId);
    //     Assertions.assertThat(user1).isEqualTo(user);
    // }
    //
    // @Test
    // @DisplayName(value = "아이디 중복체크 테스트")
    // public void idDupTest() throws Exception {
    //     User user = new User("1","1","1","1");
    //     authService.join(user);
    //     assertThrows(DuplicateLoginIdException.class, ()->{
    //         authService.idDuplicateCheck("1");
    //     });
    // }

}