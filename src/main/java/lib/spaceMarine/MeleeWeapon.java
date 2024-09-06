package lib.spaceMarine;

/** Виды оружия */
public enum MeleeWeapon {

    CHAIN_SWORD("CHAIN_SWORD"),
    POWER_SWORD("POWER_SWORD"),
    MANREAPER("MANREAPER"),
    POWER_FIST("POWER_FIRST");
    private String weapon;
    private String value;
    private static final MeleeWeapon[] values = {CHAIN_SWORD, POWER_SWORD, MANREAPER, POWER_FIST};

    MeleeWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getWeapon() {
        return this.weapon;
    }

    public static MeleeWeapon getValue(String value) {
        for (MeleeWeapon meleeWeapon : values) {
            if (meleeWeapon.toString().equals((value).toUpperCase())) {
                return meleeWeapon;
        }
        }
        return null;
    }

    /**
     * Список с параметрами enum
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var MeleeWeapon : values()) {
            nameList.append(MeleeWeapon.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }


}