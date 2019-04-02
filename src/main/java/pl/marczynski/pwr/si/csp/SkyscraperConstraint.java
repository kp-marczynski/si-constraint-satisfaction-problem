package pl.marczynski.pwr.si.csp;

public class SkyscraperConstraint {
    private final SkyscrapperConstraintType type;
    private final int index;
    private final int visibleSkyScrappers;

    public SkyscraperConstraint(SkyscrapperConstraintType type, int index, int visibleSkyScrappers) {
        this.type = type;
        this.index = index;
        this.visibleSkyScrappers = visibleSkyScrappers;
    }

    public SkyscrapperConstraintType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getVisibleSkyScrappers() {
        return visibleSkyScrappers;
    }

    @Override
    public String toString() {
        return type +
                ", index=" + index +
                ", visible=" + visibleSkyScrappers;
    }
}
