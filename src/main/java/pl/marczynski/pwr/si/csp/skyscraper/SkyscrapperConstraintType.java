package pl.marczynski.pwr.si.csp.skyscraper;

public enum SkyscrapperConstraintType {
    UP, DOWN, LEFT, RIGHT;

    public static SkyscrapperConstraintType getFromString(String type) {
        switch (type) {
            case "G":
                return UP;
            case "D":
                return DOWN;
            case "P":
                return RIGHT;
            case "L":
                return LEFT;
            default:
                throw new IllegalStateException("Unrecognized type of Skyscraper constraint");
        }
    }
}