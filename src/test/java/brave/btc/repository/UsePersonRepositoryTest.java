package brave.btc.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import brave.btc.domain.user.UsePerson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
class UsePersonRepositoryTest {

    @Autowired
    UsePersonRepository usePersonRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("사용자 조회 테스트 - ID")
    public void findById() throws Exception {
        //given
        UsePerson usePerson = new UsePerson();
        //when
        usePersonRepository.save(usePerson);
        Optional<UsePerson> optionalUsePerson = usePersonRepository.findById(usePerson.getId());
        UsePerson findUsePerson = optionalUsePerson.get();
        Optional<UsePerson> optionalNullUsePerson = usePersonRepository.findById(2L);
        UsePerson nullUsePerson = optionalNullUsePerson.orElse(null);
        //then
        assertThat(findUsePerson.getId()).isEqualTo(usePerson.getId());
        assertThat(nullUsePerson).isNull();
    }

    @Test
    @DisplayName("사용자 조회 테스트 - ALL")
    public void findAll() throws Exception {
        //given
        UsePerson usePerson1 = UsePerson.builder()
                .build();
        UsePerson usePerson2 = UsePerson.builder()
                .build();
        //when
        usePersonRepository.save(usePerson1);
        usePersonRepository.save(usePerson2);
        List<UsePerson> all = usePersonRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 조회 테스트 - NAME")
    public void findByName() throws Exception {
        //given
        UsePerson usePerson1 = UsePerson.builder()
                .name("usePerson1")
                .build();
        UsePerson usePerson2 = UsePerson.builder()
                .name("usePerson2")
                .build();
        //when
        usePersonRepository.save(usePerson1);
        usePersonRepository.save(usePerson2);
        List<UsePerson> all = usePersonRepository.findByName("usePerson1");
        //then
        assertThat(all.get(0).getName()).isEqualTo("usePerson1");
    }

    @Test
    @DisplayName("사용자 조회 테스트 - LOGINID")
    public void findByLoginId() throws Exception {
        //given
        UsePerson usePerson1 = UsePerson.builder()
                .loginId("kang")
                .build();
        UsePerson usePerson2 = UsePerson.builder()
                .loginId("kim")
                .build();
        //when
        usePersonRepository.save(usePerson1);
        usePersonRepository.save(usePerson2);
        Optional<UsePerson> optionalUsePerson = usePersonRepository.findByLoginId("kang");
        UsePerson findUsePerson = optionalUsePerson.get();
        //then
        assertThat(findUsePerson.getLoginId()).isEqualTo("kang");
    }


}