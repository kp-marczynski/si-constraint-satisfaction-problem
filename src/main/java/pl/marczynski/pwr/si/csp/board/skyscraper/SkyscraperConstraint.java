package pl.marczynski.pwr.si.csp.board.skyscraper;

class SkyscraperConstraint {
    private final SkyscraperConstraintType type;
    private final int index;
    private final int visibleSkyScrappers;

    SkyscraperConstraint(SkyscraperConstraintType type, int index, int visibleSkyScrappers) {
        this.type = type;
        this.index = index;
        this.visibleSkyScrappers = visibleSkyScrappers;
    }

    SkyscraperConstraintType getType() {
        return type;
    }

    int getIndex() {
        return index;
    }

    int getVisibleSkyScrappers() {
        return visibleSkyScrappers;
    }

    @Override
    public String toString() {
        return type +
                ", index=" + index +
                ", visible=" + visibleSkyScrappers;
    }
}
