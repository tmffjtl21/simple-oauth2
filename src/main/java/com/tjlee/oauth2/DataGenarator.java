package com.tjlee.oauth2;

import com.tjlee.oauth2.domain.User;
import com.tjlee.oauth2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

/**
 * CommandLineRunner와 ApplicationRunner의 차이점 : 인자로 받는 부분이 틀림 나머진 똑같음 ( 구동시점에 run() 실행 )
 * */
@Component
public class DataGenarator implements CommandLineRunner {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public DataGenarator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        IntStream.range(0, 10).forEach(s -> {
            User user = new User();
            user.setUsername("tjlee" + s);
            user.setPassword(passwordEncoder.encode("tjlee" + s));
            userRepository.save(user);
        });
    }
}
