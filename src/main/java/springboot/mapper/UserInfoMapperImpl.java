package springboot.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import springboot.dto.UserInfo;
import springboot.entity.User;

@Component
@NoArgsConstructor
public class UserInfoMapperImpl implements UserInfoMapper{
    @Override
    public User userInfoToUser(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        } else {
            User.UserBuilder user = User.builder();
            //user.id(userInfo.getId());
            user.name(userInfo.getName());
            user.email(userInfo.getEmail());
            user.password(userInfo.getPassword());
            user.roles(userInfo.getRoles());
            return user.build();
        }
    }

    @Override
    public UserInfo customerToCustomerInfo(User customer) {
        if (customer == null) {
            return null;
        } else {
            UserInfo.UserInfoBuilder userInfo = UserInfo.builder();
            userInfo.name(customer.getName());
            userInfo.email(customer.getEmail());
            userInfo.password(customer.getPassword());
            userInfo.roles(customer.getRoles());
            return userInfo.build();
        }
    }
}
