package org.sopt.week3.service.user;

import lombok.RequiredArgsConstructor;
import org.sopt.week3.dto.response.SignInResponse;
import org.sopt.week3.repository.user.UserEntity;
import org.sopt.week3.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(final String username, final String password, final String nickname) {
        final List<UserDomain> userDomains = userRepository.findAll().stream()
                .map(this::convertToDomain)
                .toList();

        checkDuplicatedUsername(userDomains, username);

        userRepository.save(UserEntity.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build()
        );
    }

    @Transactional
    public SignInResponse signIn(final String username, final String password) {
        final UserDomain userDomain = convertToDomain(userRepository.findByUsernameAndPassword(username, password));

        return SignInResponse.of(userDomain.id(), userDomain.nickname());
    }

    private UserDomain convertToDomain(final UserEntity user) {
        return UserDomain.of(user.getId(), user.getUsername(), user.getPassword(), user.getNickname());
    }

    private void checkDuplicatedUsername(final List<UserDomain> userDomains, final String username) {
        for (UserDomain userDomain : userDomains) {
            userDomain.checkDuplicatedUsername(username);
        }
    }
}
