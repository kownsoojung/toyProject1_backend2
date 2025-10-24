package toy.project.apiserver.domain.common.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequestDto {

    @NotBlank(message = "Refresh Token을 입력해주세요.")
    private String refreshToken;
}

