package toy.project.apiserver.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import toy.project.apiserver.common.dto.ApiResponse;

/**
 * ResponseEntity 생성 유틸리티
 */
public class ResponseUtil {

    /**
     * 200 OK - 성공 응답 (데이터만)
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * 200 OK - 성공 응답 (데이터 + 메시지)
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    /**
     * 200 OK - 성공 응답 (메시지만)
     */
    public static <T> ResponseEntity<ApiResponse<T>> successMessage(String message) {
        return ResponseEntity.ok(ApiResponse.successMessage(message));
    }

    /**
     * 201 CREATED - 생성 성공
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(data, "리소스가 생성되었습니다."));
    }

    /**
     * 201 CREATED - 생성 성공 (메시지 커스텀)
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(data, message));
    }

    /**
     * 204 NO CONTENT - 성공 (응답 데이터 없음)
     */
    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    /**
     * 400 BAD REQUEST - 잘못된 요청
     */
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(message));
    }

    /**
     * 400 BAD REQUEST - 잘못된 요청 (에러코드 포함)
     */
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message, String errorCode) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(message, errorCode));
    }

    /**
     * 401 UNAUTHORIZED - 인증 실패
     */
    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(message, "UNAUTHORIZED"));
    }

    /**
     * 403 FORBIDDEN - 권한 없음
     */
    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(message, "FORBIDDEN"));
    }

    /**
     * 404 NOT FOUND - 리소스 없음
     */
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(message, "NOT_FOUND"));
    }

    /**
     * 409 CONFLICT - 충돌 (중복 등)
     */
    public static <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(message, "CONFLICT"));
    }

    /**
     * 500 INTERNAL SERVER ERROR - 서버 오류
     */
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(message, "INTERNAL_SERVER_ERROR"));
    }

    /**
     * 커스텀 상태 코드 응답
     */
    public static <T> ResponseEntity<ApiResponse<T>> status(HttpStatus status, T data, String message) {
        return ResponseEntity.status(status)
                .body(ApiResponse.success(data, message));
    }

    /**
     * 커스텀 상태 코드 에러 응답
     */
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, String errorCode) {
        return ResponseEntity.status(status)
                .body(ApiResponse.error(message, errorCode));
    }
}

