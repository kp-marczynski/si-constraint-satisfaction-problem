package pl.marczynski.pwr.si.csp.skyscraper;

enum SkyscraperConstraintType {
    UP(false), DOWN(true), LEFT(false), RIGHT(true);

    SkyscraperConstraintType(boolean reversedOrder) {
        this.reversedOrder = reversedOrder;
    }

    private final boolean reversedOrder;

    public boolean hasReversedOrder() {
        return reversedOrder;
    }

    static SkyscraperConstraintType getFromString(String type) {
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
