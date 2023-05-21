package ru.vsu.cs.timetable.exception;

public class EquipmentException extends RuntimeException {
    public enum CODE {
        EQUIPMENT_NOT_EXIST("Equipment with given name don't exists"),
        ;

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public String getCodeDescription() {
            return codeDescription;
        }

        public EquipmentException get() {
            return new EquipmentException(this, this.codeDescription);
        }

        public EquipmentException get(String msg) {
            return new EquipmentException(this, this.codeDescription + " : " + msg);
        }

        public EquipmentException get(Throwable e) {
            return new EquipmentException(this, e, this.codeDescription + " : " + e.getMessage());
        }

        public EquipmentException get(Throwable e, String msg) {
            return new EquipmentException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected CODE code;

    protected EquipmentException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected EquipmentException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

    public CODE getCode() {
        return code;
    }

}
