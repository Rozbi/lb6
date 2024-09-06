package lib.spaceMarine;

/** тип SpaceMarine */
    public enum AstartesCategory {
        ASSAULT("ASSAULT"),
        INCEPTOR("INCEPTOR"),
        SUPPRESSOR("SUPRESSOR"),
        TERMINATOR("TERMINATOR"),
        LIBRARIAN("LIBRARIAN");
        public String category;
        private static final AstartesCategory[] values = {ASSAULT, INCEPTOR, SUPPRESSOR, TERMINATOR, LIBRARIAN};
        private AstartesCategory(String category) {
            this.category = category;
        }

        public static AstartesCategory getValue(String value) {
        for (AstartesCategory astartesCategory : values) {
            if (astartesCategory.toString().equals((value).toUpperCase())) {
                return astartesCategory;
        }
        }
        return null;
    }

        public static String names(){
        StringBuilder nameList = new StringBuilder();
        for (var AstartesCategory : values()) {
            nameList.append(AstartesCategory.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

        public String getCategory() {
            return this.category;
        }
    }