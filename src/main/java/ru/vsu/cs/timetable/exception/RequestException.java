package ru.vsu.cs.timetable.exception;

public class RequestException extends RuntimeException {
    public enum CODE {
        WRONG_SUBJECT_HOUR_PER_WEEK("Subject hour per week should be a multiple 0.75"),
        MOVE_CLASS_TIME_CONFLICT("Lecturer have class at this time")
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public RequestException get() {
            return new RequestException(this, this.codeDescription);
        }

        public RequestException get(String msg) {
            return new RequestException(this, this.codeDescription + " : " + msg);
        }

        public RequestException get(Throwable e) {
            return new RequestException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public RequestException get(Throwable e, String msg) {
            return new RequestException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected RequestException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected RequestException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
