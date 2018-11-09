package db.prj.BTS.domain;

public enum Role {
    MANAGER(1), TRADER(2), CLIENT(3);
    private int value;
    private Role(int value) { this.setValue(value); }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
