package ru.vsu.cs.timetable.exception;

public class GroupException extends RuntimeException {
    public enum CODE {
        ID_NOT_FOUND("Group with given id not found"),
        GROUP_FACULTY_ALREADY_PRESENT("Group with given number and course on this faculty already present");
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public GroupException get() {
            return new GroupException(this, this.codeDescription);
        }

        public GroupException get(String msg) {
            return new GroupException(this, this.codeDescription + " : " + msg);
        }

        public GroupException get(Throwable e) {
            return new GroupException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public GroupException get(Throwable e, String msg) {
            return new GroupException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected GroupException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected GroupException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
