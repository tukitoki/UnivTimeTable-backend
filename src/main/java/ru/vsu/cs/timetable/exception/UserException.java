package ru.vsu.cs.timetable.exception;

public class UserException extends RuntimeException {
    public enum CODE {
        USERNAME_NOT_FOUND("User with given username not found"),
        ID_NOT_FOUND("User with given id not found"),
        USERNAME_ALREADY_PRESENT("User with given username already present"),
        EMAIL_ALREADY_PRESENT("User with given email already present"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public UserException get() {
            return new UserException(this, this.codeDescription);
        }

        public UserException get(String msg) {
            return new UserException(this, this.codeDescription + " : " + msg);
        }

        public UserException get(Throwable e) {
            return new UserException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public UserException get(Throwable e, String msg) {
            return new UserException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected UserException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected UserException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
