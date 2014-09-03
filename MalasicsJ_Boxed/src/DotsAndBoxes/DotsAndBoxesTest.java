package DotsAndBoxes;

import junit.framework.TestCase;

public class DotsAndBoxesTest extends TestCase {

    public void testScore() throws Exception {

    }

    /** 1
     * ._._. . .
     * |   |
     * ._._. . .
     *
     * . . . . .
     *
     * . . . . .
     *
     * . . . . .
     * @throws Exception
     */
    public void testCountDoubleCrosses() throws Exception {
        DotsAndBoxes dotsAndBoxes = new DotsAndBoxes(5, 5);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 0, 1, 0);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 0, 2, 0);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 0, 2, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 2, 1, 1, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 1, 1, 0, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 0, 1, 0, 0);
        System.out.println("Double crosses: " + dotsAndBoxes.countDoubleCrosses());
    }

    /** 1
     * ._._. . .
     * |   |
     * . . . . .
     * |   |
     * ._._. . .
     *
     * . . . . .
     *
     * . . . . .
     * @throws Exception
     */
    public void testCountCycles() throws Exception {
        DotsAndBoxes dotsAndBoxes = new DotsAndBoxes(5, 5);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 0, 1, 0);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 0, 2, 0);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 0, 2, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 2, 1, 2, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 2, 1, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 2, 0, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 2, 0, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 0, 1, 0, 0);
        System.out.println("Cycles: " + dotsAndBoxes.countCycles());
    }

    /** 4
     * . . ._._.
     * | |
     * . ._._._.
     * |       |
     * ._._._. .
     *       | |
     * ._._. . .
     *     | | |
     * ._. . . .
     * @throws Exception
     */
    public void testCountOpenChains() throws Exception {
        DotsAndBoxes dotsAndBoxes = new DotsAndBoxes(5, 5);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 0, 0, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 0, 1, 0, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 2, 1, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 2, 2, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 2, 3, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 3, 2, 3, 3);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 3, 3, 3, 4);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 0, 1, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 1, 1, 2, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 2, 1, 3, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 3, 1, 4, 1);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 4, 1, 4, 2);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 4, 2, 4, 3);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 4, 3, 4, 4);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 0, 3, 1, 3);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 1, 3, 2, 3);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 3, 2, 4);

        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_TWO, 0, 4, 1, 4);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 2, 0, 3, 0);
        dotsAndBoxes.drawLine(DotsAndBoxes.PLAYER_ONE, 3, 0, 4, 0);

        System.out.println("Open chains: " + dotsAndBoxes.countOpenChains());
    }
}