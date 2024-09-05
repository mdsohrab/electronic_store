package Electronic.Store.Electronic.Store.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String about;

    private String imageName;
}
