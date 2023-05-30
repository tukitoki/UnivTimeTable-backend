package ru.vsu.cs.timetable.exception;

public class AudienceException extends RuntimeException {
    public enum CODE {
        AUDIENCE_ALREADY_EXIST("Audience with that number already exists"),
        ID_NOT_FOUND("Audience with given id not found"),
        AUDIENCE_FACULTY_NUMBER_NOT_FOUND("Audience with given number at this faculty not found"),
        AUDIENCE_IS_BUSY_FOR_LESSON("Audience already busy for the lesson")
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public AudienceException get() {
            return new AudienceException(this, this.codeDescription);
        }

        public AudienceException get(String msg) {
            return new AudienceException(this, this.codeDescription + " : " + msg);
        }

        public AudienceException get(Throwable e) {
            return new AudienceException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public AudienceException get(Throwable e, String msg) {
            return new AudienceException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected AudienceException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected AudienceException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
