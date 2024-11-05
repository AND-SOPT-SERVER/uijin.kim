package org.sopt.week3.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(final long userId);

    UserEntity findByUsernameAndPassword(final String username, final String password);
}
