package ru.vsu.cs.timetable.exception;

public class TimetableException extends RuntimeException {
    public enum CODE {
        ADMIN_CANT_ACCESS("Admin can't get timetable"),
        TIMETABLE_CANT_BE_GENERATED("Timetable can't be generated due some mistakes"),
        TIMETABLE_WAS_ALREADY_MADE("Timetable for that faculty was already made"),
        TIMETABLE_WAS_NOT_MADE("Timetable for that user wasn't made"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public TimetableException get() {
            return new TimetableException(this, this.codeDescription);
        }

        public TimetableException get(String msg) {
            return new TimetableException(this, this.codeDescription + " : " + msg);
        }

        public TimetableException get(Throwable e) {
            return new TimetableException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public TimetableException get(Throwable e, String msg) {
            return new TimetableException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected TimetableException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected TimetableException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
