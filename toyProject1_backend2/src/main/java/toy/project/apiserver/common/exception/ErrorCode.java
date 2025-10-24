package toy.project.apiserver.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  
    // 공통 에러 (1000번대)
    INVALID_INPUT_VALUE("E1001", "잘못된 입력값입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_VALUE("E1002", "잘못된 타입입니다.", HttpStatus.BAD_REQUEST),
    MISSING_INPUT_VALUE("E1003", "필수 입력값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("E1004", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    METHOD_NOT_ALLOWED("E1005", "허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    ACCESS_DENIED("E1006", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 인증/인가 에러 (2000번대)
    UNAUTHORIZED("E2001", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("E2002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("E2003", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("E2004", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_DISABLED("E2005", "비활성화된 계정입니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("E2006", "잠긴 계정입니다.", HttpStatus.UNAUTHORIZED),

    // 사용자 관련 에러 (3000번대)
    USER_NOT_FOUND("E3001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USERNAME("E3002", "이미 존재하는 사용자명입니다.", HttpStatus.CONFLICT),
    DUPLICATE_EMAIL("E3003", "이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_PASSWORD("E3004", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT("E3005", "비밀번호는 최소 6자 이상이어야 합니다.", HttpStatus.BAD_REQUEST),

    // 리소스 관련 에러 (4000번대)
    RESOURCE_NOT_FOUND("E4001", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS("E4002", "이미 존재하는 리소스입니다.", HttpStatus.CONFLICT),
    RESOURCE_DELETED("E4003", "삭제된 리소스입니다.", HttpStatus.GONE),

    // 데이터베이스 에러 (5000번대)
    DATABASE_ERROR("E5001", "데이터베이스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY("E5002", "중복된 키입니다.", HttpStatus.CONFLICT),
    DATA_INTEGRITY_VIOLATION("E5003", "데이터 무결성 위반입니다.", HttpStatus.BAD_REQUEST),

    // 비즈니스 로직 에러 (6000번대)
    BUSINESS_LOGIC_ERROR("E6001", "비즈니스 로직 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSION("E6002", "권한이 부족합니다.", HttpStatus.FORBIDDEN),
    OPERATION_NOT_ALLOWED("E6003", "허용되지 않은 작업입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}

