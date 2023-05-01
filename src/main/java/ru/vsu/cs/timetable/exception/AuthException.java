package ru.vsu.cs.timetable.exception;

public class AuthException extends RuntimeException {
    public enum CODE {
        JWT_VALIDATION_ERROR("JWT validation error"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public AuthException get() {
            return new AuthException(this, this.codeDescription);
        }

        public AuthException get(String msg) {
            return new AuthException(this, this.codeDescription + " : " + msg);
        }

        public AuthException get(Throwable e) {
            return new AuthException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public AuthException get(Throwable e, String msg) {
            return new AuthException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected AuthException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected AuthException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
