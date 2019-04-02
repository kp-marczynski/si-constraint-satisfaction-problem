package pl.marczynski.pwr.si.csp;

public class FutoshikiConstraint {
    private final FieldId smaller;
    private final FieldId bigger;

    public FutoshikiConstraint(FieldId smaller, FieldId bigger) {
        this.smaller = smaller;
        this.bigger = bigger;
    }

    public FieldId getSmaller() {
        return smaller;
    }

    public FieldId getBigger() {
        return bigger;
    }

    @Override
    public String toString() {
        return smaller + ";" + bigger;
    }
}
