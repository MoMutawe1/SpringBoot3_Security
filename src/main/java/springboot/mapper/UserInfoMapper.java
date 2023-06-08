package springboot.mapper;

import org.mapstruct.Mapper;
import springboot.dto.UserInfo;
import springboot.entity.User;

@Mapper
public interface UserInfoMapper {
    User userInfoToUser(UserInfo customerDTO);
    UserInfo customerToCustomerInfo(User customer);
}
