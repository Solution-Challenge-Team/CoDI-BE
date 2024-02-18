package koders.codi.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "400", "값이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "405", "지원하지 않는 Http Method 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "400", "입력 값의 타입이 올바르지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "401", "잘못된 접근입니다."),
    NOT_EXIST_TOKEN_INFO(HttpStatus.FORBIDDEN, "403", "토큰 정보가 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "403", "접근이 거부 되었습니다."),
    FEIGN_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "400", "정보를 가져올 수 없습니다."),

    // User
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 사용자입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "400", "이미 존재하는 이메일 입니다."),
    EMAIL_OR_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "400", "이메일 혹은 비밀번호가 일치하지 않습니다."),

    // Social login
    SOCIAL_ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 소셜 로그인 토큰입니다."),

    // Image
    IMAGE_WRONG_FILE_FORMAT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "415", "이미지 파일 형식이 잘못되었습니다."),
    IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "500", "이미지 업로드에 실패하였습니다."),

    // Hospital
    NOT_FOUND_HOSPITAL(HttpStatus.NOT_FOUND, "404", "존재하지 않는 병원입니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "404", "존재하지 않는 리뷰입니다."),

    // category
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "404", "존재하지 않는 카테고리입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public int getStatus() {
        return this.status.value();
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
