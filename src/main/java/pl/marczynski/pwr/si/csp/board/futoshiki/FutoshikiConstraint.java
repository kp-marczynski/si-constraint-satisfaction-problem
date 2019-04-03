package pl.marczynski.pwr.si.csp.board.futoshiki;

import pl.marczynski.pwr.si.csp.board.FieldId;

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
