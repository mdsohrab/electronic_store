package Electronic.Store.Electronic.Store.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be in minimum 4 characters !!")
    private String title;

    @NotBlank(message = "description required !!")
    private String description;

    @NotBlank(message = "coverImage required !!")
    private String coverImage;
}
