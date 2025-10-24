package toy.project.apiserver.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * API 공통 응답 DTO
 * @param <T> 응답 데이터 타입
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * 성공 여부
     */
    private boolean success;

    /**
     * 응답 메시지
     */
    private String message;

    /**
     * 응답 데이터
     */
    private T data;

    /**
     * 에러 코드 (실패 시)
     */
    private String errorCode;

    /**
     * 응답 시간
     */
    private LocalDateTime timestamp;

    /**
     * 성공 응답 생성 (데이터만)
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Success",
                data,
                null,
                LocalDateTime.now()
        );
    }

    /**
     * 성공 응답 생성 (데이터 + 메시지)
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                message,
                data,
                null,
                LocalDateTime.now()
        );
    }

    /**
     * 성공 응답 생성 (메시지만)
     */
    public static <T> ApiResponse<T> successMessage(String message) {
        return new ApiResponse<>(
                true,
                message,
                null,
                null,
                LocalDateTime.now()
        );
    }

    /**
     * 실패 응답 생성 (메시지만)
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                null,
                LocalDateTime.now()
        );
    }

    /**
     * 실패 응답 생성 (메시지 + 에러코드)
     */
    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return new ApiResponse<>(
                false,
                message,
                null,
                errorCode,
                LocalDateTime.now()
        );
    }

    /**
     * 실패 응답 생성 (메시지 + 에러코드 + 데이터)
     */
    public static <T> ApiResponse<T> error(String message, String errorCode, T data) {
        return new ApiResponse<>(
                false,
                message,
                data,
                errorCode,
                LocalDateTime.now()
        );
    }
}

