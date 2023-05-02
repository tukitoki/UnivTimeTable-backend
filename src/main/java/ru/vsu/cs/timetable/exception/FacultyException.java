package ru.vsu.cs.timetable.exception;

public class FacultyException extends RuntimeException {
    public enum CODE {
        ID_NOT_FOUND("Faculty with given id not found"),
        NAME_ALREADY_PRESENT("Faculty with given name already present"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public FacultyException get() {
            return new FacultyException(this, this.codeDescription);
        }

        public FacultyException get(String msg) {
            return new FacultyException(this, this.codeDescription + " : " + msg);
        }

        public FacultyException get(Throwable e) {
            return new FacultyException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public FacultyException get(Throwable e, String msg) {
            return new FacultyException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected FacultyException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected FacultyException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
