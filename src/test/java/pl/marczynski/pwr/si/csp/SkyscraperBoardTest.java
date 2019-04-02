package pl.marczynski.pwr.si.csp;

import org.junit.Test;

public class SkyscraperBoardTest {
    @Test
    public void shouldInitializeFutoshikiBoard() {
        //given
        String fileName = "skyscrapper_5_3";

        //when
        SkyscraperBoard skyscraperBoard = SkyscraperBoard.initializeFromFile(fileName);

        //then
        System.out.println(skyscraperBoard);
    }
}
