package springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import springboot.dto.UserInfo;
import springboot.entity.User;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);
}