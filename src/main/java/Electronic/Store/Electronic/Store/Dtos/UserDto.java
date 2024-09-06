package Electronic.Store.Electronic.Store.Dtos;

import Electronic.Store.Electronic.Store.Validate.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3, max = 15, message = "Invalid name!!")
    private String name;

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "In Valid Email !!")
    @NotBlank(message = "Email is required!!")
    private String email;

    @NotBlank(message = "Password is required !!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;

    @Size(min = 10, max = 1000, message = "Write about yourself !!")
    @NotBlank(message = "About is required")
    private String about;

    @ImageNameValid
    private String imageName;
}
