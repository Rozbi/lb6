package lib.spaceMarine;

/** тип SpaceMarine */
    public enum AstartesCategory {
        ASSAULT("ASSAULT"),
        INCEPTOR("INCEPTOR"),
        SUPPRESSOR("SUPRESSOR"),
        TERMINATOR("TERMINATOR"),
        LIBRARIAN("LIBRARIAN");
        public String category;

        private AstartesCategory(String category) {
            this.category = category;
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