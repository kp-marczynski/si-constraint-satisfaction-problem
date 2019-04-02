package pl.marczynski.pwr.si.csp.futoshiki;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FutoshikiBoardTest {

    @Test
    public void shouldSubstractLetterA() {
        //given
        String position = "B1";

        //when
        int result = position.charAt(0) - 'A';

        //then
        assertEquals(result, 1);
    }

    @Test
    public void shouldInitializeFutoshikiBoard() {
        //given
        String fileName = "futoshiki_5_3";

        //when
        FutoshikiBoard futoshikiBoard = FutoshikiBoard.initializeFromFile(fileName);

        //then
        System.out.println(futoshikiBoard);
    }
}
