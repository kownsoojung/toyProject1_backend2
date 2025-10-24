package toy.project.apiserver.common.exception;

/**
 * 리소스를 찾을 수 없는 예외
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }

    public static ResourceNotFoundException userNotFound() {
        return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    public static ResourceNotFoundException userNotFound(String username) {
        return new ResourceNotFoundException(
                ErrorCode.USER_NOT_FOUND,
                String.format("사용자를 찾을 수 없습니다: %s", username)
        );
    }
}

