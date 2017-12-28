package kr.ac.knu.service;

import kr.ac.knu.domain.KnuUser;
import kr.ac.knu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rokim on 2017. 6. 11..
 */
@Service
public class ExampleAopService {
    @Autowired private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void dealUserPoint(KnuUser from, KnuUser to, long count) throws Exception {
        from.setPoint(from.getPoint() - count);
        to.setPoint(to.getPoint() + count);

        userRepository.save(from);
        if (true) {
            throw new Exception();
        }
        userRepository.save(to);
    }
}
