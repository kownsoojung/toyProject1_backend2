package toy.project.apiserver.domain.common.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long userId;
    private String username;
    private String name;
    private String email;
    private String role;
    private Integer centerId;

    public static LoginResponseDto of(String accessToken, UserDto userDto) {
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .centerId(userDto.getCenterId())
                .build();
    }
}

