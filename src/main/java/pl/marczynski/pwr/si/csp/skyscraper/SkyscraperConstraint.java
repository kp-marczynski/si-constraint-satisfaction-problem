package pl.marczynski.pwr.si.csp.skyscraper;

class SkyscraperConstraint {
    private final SkyscrapperConstraintType type;
    private final int index;
    private final int visibleSkyScrappers;

    SkyscraperConstraint(SkyscrapperConstraintType type, int index, int visibleSkyScrappers) {
        this.type = type;
        this.index = index;
        this.visibleSkyScrappers = visibleSkyScrappers;
    }

    SkyscrapperConstraintType getType() {
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
