package ru.vsu.cs.timetable.exception;

public class UniversityException extends RuntimeException {
    public enum CODE {
        ID_NOT_FOUND("University with given id not found"),
        UNIVERSITY_NAME_NOT_FOUND("University with given name already present"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public UniversityException get() {
            return new UniversityException(this, this.codeDescription);
        }

        public UniversityException get(String msg) {
            return new UniversityException(this, this.codeDescription + " : " + msg);
        }

        public UniversityException get(Throwable e) {
            return new UniversityException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public UniversityException get(Throwable e, String msg) {
            return new UniversityException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected UniversityException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected UniversityException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
