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
            state.gridX = Integer.parseInt(dimensions[0]);
            state.gridY = Integer.parseInt(dimensions[1]);

            // Initialize the board
            state.board = new char[state.gridX][state.gridY];

            for (int i = 0; i < state.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < state.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    state.board[i][j] = cell;
                    if (cell=='R')
                        state.goals.add(new Goal(cell,i, j, Color.RED));
                    if(cell== 'G')
                        state.goals.add(new Goal(cell,i, j,Color.GREEN));
                    if(cell== 'B')
                        state.goals.add(new Goal(cell,i, j,Color.BLUE));
                    if(cell== 'Y')
                        state.goals.add(new Goal(cell,i, j,Color.YELLOW));
                    if(cell == 'P')
                        state.goals.add(new Goal(cell,i,j,Color.PINK));
                    if(cell == '?')
                        state.goals.add(new Goal(cell,i,j,Color.DARK_GRAY));
                }
            }
            for (int i = 0; i < state.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < state.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    if (cell=='r')
                        state.stones.add(new Stone(cell,Color.RED, i, j, false));
                    if (cell=='g')
                        state.stones.add(new Stone(cell,Color.GREEN, i, j, false));
                    if (cell=='b')
                        state.stones.add(new Stone(cell,Color.BLUE, i, j, false));
                    if (cell=='y')
                        state.stones.add(new Stone(cell,Color.YELLOW, i, j, false));
                    if(cell == 'p')
                        state.stones.add(new Stone(cell,Color.PINK, i, j, false));
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
