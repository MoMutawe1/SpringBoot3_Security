package springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Rather than passing each and every value individually to the authenticateAndGe method in the controller class, It's better to pass dto object.
public class AuthRequest {

    private String username;
    private String password;
}