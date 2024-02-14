package koders.codi.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max =16)
    private String password;
    @NotBlank
    private String nickname;
    private MultipartFile profileImage;
}