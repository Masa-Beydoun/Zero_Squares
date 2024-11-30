import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputStates {

    public static State readGrid(int index){
        State state = new State();
        String filePath = "src/grids/"+index+".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read grid dimensions
            String[] dimensions = br.readLine().split(" ");
            int gridX = Short.parseShort(dimensions[0]);
            int gridY = Short.parseShort(dimensions[1]);

            // Initialize the board
            state.board = new char[gridX][gridY];

            for (int i = 0; i < state.board.length; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < state.board[0].length; j++) {
                    char cell = cells[j].charAt(0);
                    state.board[i][j] = cell;
                   }
            }
            for (int i = 0; i < state.board.length; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < state.board[0].length; j++) {
                    char cell = cells[j].charAt(0);
                    if(cell != '#' && cell != '_' && cell != 'O' && cell != 'B' && cell != 'Y'&& cell != 'G'&& cell != 'R'&& cell != 'P')
                        state.stones.add(new Stone(cell, i, j, false));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("initial grid: ");
//        state.printGrid();
        System.out.println(state);
        return state;

    }


}
