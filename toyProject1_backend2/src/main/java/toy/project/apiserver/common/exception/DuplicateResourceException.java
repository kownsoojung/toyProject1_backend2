package toy.project.apiserver.common.exception;

/**
 * 중복된 리소스 예외
 */
public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicateResourceException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }

    public static DuplicateResourceException duplicateUsername(String username) {
        return new DuplicateResourceException(
                ErrorCode.DUPLICATE_USERNAME,
                String.format("이미 존재하는 사용자명입니다: %s", username)
        );
    }

    public static DuplicateResourceException duplicateEmail(String email) {
        return new DuplicateResourceException(
                ErrorCode.DUPLICATE_EMAIL,
                String.format("이미 존재하는 이메일입니다: %s", email)
        );
    }
}

