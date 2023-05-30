package ru.vsu.cs.timetable.exception;

public class ClassException extends RuntimeException {
    public enum CODE {
        CLASS_SUBJECT_NOT_FOUND("Class with that subject not found"),
        WRONG_CLASS_FOUND("Found incorrect class in timetable"),
        INCORRECT_CLASS_TO_MOVE("Incorrect class to move typed")
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public ClassException get() {
            return new ClassException(this, this.codeDescription);
        }

        public ClassException get(String msg) {
            return new ClassException(this, this.codeDescription + " : " + msg);
        }

        public ClassException get(Throwable e) {
            return new ClassException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public ClassException get(Throwable e, String msg) {
            return new ClassException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected ClassException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected ClassException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
