package brave.btc.repository;

import brave.btc.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("사용자 조회 테스트 - ID")
    public void findById() throws Exception {
        //given
        User user = User.builder()
                .build();
        //when
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User findUser = optionalUser.get();
        Optional<User> optionalNullUser = userRepository.findById(2L);
        User nullUser = optionalNullUser.orElse(null);
        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(nullUser).isNull();
    }

    @Test
    @DisplayName("사용자 조회 테스트 - ALL")
    public void findAll() throws Exception {
        //given
        User user1 = User.builder()
                .build();
        User user2 = User.builder()
                .build();
        //when
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> all = userRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 조회 테스트 - NAME")
    public void findByName() throws Exception {
        //given
        User user1 = User.builder()
                .name("user1")
                .build();
        User user2 = User.builder()
                .name("user2")
                .build();
        //when
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> all = userRepository.findByName("user1");
        //then
        assertThat(all.get(0).getName()).isEqualTo("user1");
    }

    @Test
    @DisplayName("사용자 조회 테스트 - LOGINID")
    public void findByLoginId() throws Exception {
        //given
        User user1 = User.builder()
                .loginId("kang")
                .build();
        User user2 = User.builder()
                .loginId("kim")
                .build();
        //when
        userRepository.save(user1);
        userRepository.save(user2);
        Optional<User> optionalUser = userRepository.findByLoginId("kang");
        User findUser = optionalUser.get();
        //then
        assertThat(findUser.getLoginId()).isEqualTo("kang");
    }


}