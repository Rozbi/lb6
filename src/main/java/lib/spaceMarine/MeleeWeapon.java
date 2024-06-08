package lib.spaceMarine;

/** Виды оружия */
public enum MeleeWeapon {

        CHAIN_SWORD("CHAIN_SWORD"),
        POWER_SWORD("POWER_SWORD"),
        MANREAPER("MANREAPER"),
        POWER_FIST("POWER_FIRST");
        private String weapon;

        private MeleeWeapon(String weapon) {
            this.weapon = weapon;
        }
        public String getWeapon() {
            return this.weapon;
        }
        /** Список с параметрами enum */
        public static String names(){
        StringBuilder nameList = new StringBuilder();
        for (var MeleeWeapon : values()) {
            nameList.append(MeleeWeapon.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

    }