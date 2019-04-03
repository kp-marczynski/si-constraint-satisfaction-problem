package pl.marczynski.pwr.si.csp.game.heuristics;

import pl.marczynski.pwr.si.csp.board.Board;
import pl.marczynski.pwr.si.csp.board.FieldId;

public interface Heuristics {
    FieldId getNextField(Board board);
}
