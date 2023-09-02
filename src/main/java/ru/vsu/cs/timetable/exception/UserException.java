package ru.vsu.cs.timetable.exception;

public class UserException extends RuntimeException {
    public enum CODE {
        USERNAME_NOT_FOUND("User with given username not found"),
        EMAIL_NOT_FOUND("User with given email not found"),
        ID_NOT_FOUND("User with given id not found"),
        CANT_DELETE_YOURSELF("Admin cant delete yourself"),
        USERNAME_ALREADY_PRESENT("User with given username already present"),
        EMAIL_ALREADY_PRESENT("User with given email already present"),
        ADMIN_CANT_HAVE_UNIV("Admin can't have university"),
        ADMIN_CANT_HAVE_FACULTY("Admin can't have faculty"),
        ADMIN_CANT_HAVE_GROUP("Admin can't have group"),
        LECTURER_SHOULD_HAVE_UNIVERSITY("Lecturer should have university"),
        LECTURER_SHOULD_HAVE_FACULTY("Lecturer should have faculty"),
        LECTURER_CANT_HAVE_GROUP("Lecturer can't have group"),
        HEADMAN_SHOULD_HAVE_UNIVERSITY("Headman should have university"),
        HEADMAN_SHOULD_HAVE_FACULTY("Headman should have faculty"),
        HEADMAN_SHOULD_HAVE_GROUP("Headman should have group"),
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
