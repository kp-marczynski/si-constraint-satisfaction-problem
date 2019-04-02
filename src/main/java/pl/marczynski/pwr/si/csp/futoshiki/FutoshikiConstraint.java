package pl.marczynski.pwr.si.csp.futoshiki;

import pl.marczynski.pwr.si.csp.FieldId;

class FutoshikiConstraint {
    private final FieldId smaller;
    private final FieldId bigger;

    FutoshikiConstraint(FieldId smaller, FieldId bigger) {
        this.smaller = smaller;
        this.bigger = bigger;
    }

    FieldId getSmaller() {
        return smaller;
    }

    FieldId getBigger() {
        return bigger;
    }

    @Override
    public String toString() {
        return smaller + " < " + bigger;
    }
}
