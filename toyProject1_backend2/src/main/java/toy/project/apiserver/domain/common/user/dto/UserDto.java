package toy.project.apiserver.domain.common.user.dto;

import lombok.*;
import toy.project.apiserver.domain.common.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private String name;
    private String phone;
    private String role;
    private Boolean enabled;
    private Integer centerId;

    public static UserDto from(UserEntity entity) {
        return UserDto.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .name(entity.getName())
                .phone(entity.getPhone())
                .role(entity.getRole().name())
                .enabled(entity.getEnabled())
                .centerId(entity.getCenterId())
                .build();
    }
}

