package kr.ac.knu.service;

import kr.ac.knu.KnuWasApplication;
import kr.ac.knu.domain.KnuUser;
import kr.ac.knu.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by rokim on 2017. 6. 11..
 */

@RunWith(SpringRunner.class)
@SpringBootTest(value = "server.port:0", classes = KnuWasApplication.class)
@WebAppConfiguration
public class ExampleAopServiceTest {
    @Autowired private ExampleAopService exampleAopService;
    @Autowired private UserRepository userRepository;

    @Before
    public void setUp() {
//        userRepository.deleteAll();

        KnuUser knuUser = new KnuUser();
        knuUser.setUserId("rokim");
        knuUser.setName("김경석");
        knuUser.setEmail("rokim@riotgames.com");
        knuUser.setPoint(10000);
        userRepository.save(knuUser);

        KnuUser knuUser2 = new KnuUser();
        knuUser2.setUserId("iye");
        knuUser2.setName("예재형");
        knuUser2.setEmail("iye@riotgames.com");
        knuUser2.setPoint(5000);
        userRepository.save(knuUser2);
    }

    @Test
    public void test() throws Exception {
        KnuUser robin = userRepository.findByUserId("rokim");
        KnuUser ian = userRepository.findByUserId("iye");

        try {
            exampleAopService.dealUserPoint(robin, ian, 500);
        } catch (Exception e) {
            ;;
        }

        KnuUser afterRobin = userRepository.findByUserId("rokim");
        KnuUser afterIan = userRepository.findByUserId("iye");

        System.out.println(afterRobin.getPoint());
        System.out.println(afterIan.getPoint());
    }
}