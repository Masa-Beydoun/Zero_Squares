import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BoardGui  extends JFrame implements KeyListener {

    int index;
    BoardLogic boardLogic;
    JPanel main_panel = new JPanel();
    JButton [][]buttons;


    BoardGui(int index){
        this.index = index;
        boardLogic = new BoardLogic();
        readGrid(index);
        buttons = new JButton[boardLogic.gridX][boardLogic.gridY];
        main_panel.setLayout(new GridLayout(buttons.length,buttons[0].length));
        for(int i=0;i<buttons.length;i++){
            for(int j=0;j<buttons[i].length;j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
            }
        }
        for(int i=0;i<buttons.length;i++)
            for(int j=0;j<buttons[i].length;j++)
                main_panel.add(buttons[i][j]);
        updateFrame();
        main_panel.setSize(new Dimension(700,700));
        this.add(main_panel);
        this.setSize(700,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);


    }

    public void readGrid(int index){
        String filePath = "src/grids/"+index+".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read grid dimensions
            String[] dimensions = br.readLine().split(" ");
            boardLogic.gridX = Integer.parseInt(dimensions[0]);
            boardLogic.gridY = Integer.parseInt(dimensions[1]);

            // Initialize the board
            boardLogic.board = new char[boardLogic.gridX][boardLogic.gridY];

            // Read the grid data line by line
            for (int i = 0; i < boardLogic.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < boardLogic.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    boardLogic.board[i][j] = cell;

                    // Handle stones and goals based on cell type
                    switch (cell) {
                        case 'r':
                            boardLogic.stones.add(new Stone(Color.RED, i, j, false));
                            break;
                        case 'g':
                            boardLogic.stones.add(new Stone(Color.GREEN, i, j, false));
                            break;
                        case 'b':
                            boardLogic.stones.add(new Stone(Color.BLUE, i, j, false));
                            break;
                        case 'y':
                            boardLogic.stones.add(new Stone(Color.YELLOW, i, j, false));
                            break;
                        case 'R':
                            boardLogic.goals.add(new Goal(i, j,Color.RED));
                            break;
                        case 'G':
                            boardLogic.goals.add(new Goal(i, j,Color.GREEN));
                            break;
                        case 'B':
                            boardLogic.goals.add(new Goal(i, j,Color.BLUE));
                            break;
                        case 'Y':
                            boardLogic.goals.add(new Goal(i, j,Color.YELLOW));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the board for debugging
        for (int i = 0; i < boardLogic.board.length; i++) {
            for (int j = 0; j < boardLogic.board[i].length; j++) {
                System.out.print(boardLogic.board[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void updateFrame(){

        for(int i=0;i<boardLogic.board.length;i++){
            for (int j=0;j<boardLogic.board[i].length;j++){
                if(boardLogic.board[i][j]=='#'){
                    buttons[i][j].setBackground(Color.BLACK);
                }
                else if (boardLogic.board[i][j]=='r'){
                    buttons[i][j].setBackground(Color.RED);
                }
                else if (boardLogic.board[i][j]=='g'){
                    buttons[i][j].setBackground(Color.GREEN);
                }
                else if (boardLogic.board[i][j]=='b'){
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else if (boardLogic.board[i][j]=='y'){
                    buttons[i][j].setBackground(Color.YELLOW);
                }
                else {
                    buttons[i][j].setBackground(Color.WHITE);
                }
                if (boardLogic.board[i][j]=='R'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 10));
                }
                else if (boardLogic.board[i][j]=='G'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                }
                else if (boardLogic.board[i][j]=='B'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
                }else if (boardLogic.board[i][j]=='Y'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                }
                else{
//                    buttons[i][j].setBorder(null);
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        }
    }




    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                boardLogic.move("UP",true);
                break;
            case KeyEvent.VK_DOWN:
                boardLogic.move("DOWN",true);
                break;
            case KeyEvent.VK_LEFT:
                boardLogic.move("LEFT",true);
                break;
            case KeyEvent.VK_RIGHT:
                boardLogic.move("RIGHT",true);
                break;
        }

        updateFrame();
        repaint();
        System.out.println("grid: ");
        boardLogic.printGrid();
        if (boardLogic.checkGameOver()) {
            System.out.println("______________________________________");
            System.out.println("next level");
            this.dispose();
            index++;
            if(index<5)
                new BoardGui(index);
            return;
        }
        boardLogic.possibleBoards();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

}
