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
    FullGame fullGame=new FullGame();
    JPanel main_panel = new JPanel();
    JButton [][]buttons;


    BoardGui(int index){
        this.index = index;
        readGrid(index);
        buttons = new JButton[fullGame.boardLogic.gridX][fullGame.boardLogic.gridY];
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
            fullGame.boardLogic.gridX = Integer.parseInt(dimensions[0]);
            fullGame.boardLogic.gridY = Integer.parseInt(dimensions[1]);

            // Initialize the board
            fullGame.boardLogic.board = new char[fullGame.boardLogic.gridX][fullGame.boardLogic.gridY];

            for (int i = 0; i < fullGame.boardLogic.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < fullGame.boardLogic.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    fullGame.boardLogic.board[i][j] = cell;
                    if (cell=='R')
                        fullGame.boardLogic.goals.add(new Goal(cell,i, j,Color.RED));
                    if(cell== 'G')
                        fullGame.boardLogic.goals.add(new Goal(cell,i, j,Color.GREEN));
                    if(cell== 'B')
                        fullGame.boardLogic.goals.add(new Goal(cell,i, j,Color.BLUE));
                    if(cell== 'Y')
                        fullGame.boardLogic.goals.add(new Goal(cell,i, j,Color.YELLOW));
                    if(cell == 'P')
                        fullGame.boardLogic.goals.add(new Goal(cell,i,j,Color.PINK));
                    if(cell == '?')
                        fullGame.boardLogic.goals.add(new Goal(cell,i,j,Color.DARK_GRAY));
                }
            }
            for (int i = 0; i < fullGame.boardLogic.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < fullGame.boardLogic.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    if (cell=='r')
                            fullGame.boardLogic.stones.add(new Stone(cell,Color.RED, i, j, false));
                    if (cell=='g')
                        fullGame.boardLogic.stones.add(new Stone(cell,Color.GREEN, i, j, false));
                    if (cell=='b')
                        fullGame.boardLogic.stones.add(new Stone(cell,Color.BLUE, i, j, false));
                    if (cell=='y')
                        fullGame.boardLogic.stones.add(new Stone(cell,Color.YELLOW, i, j, false));
                    if(cell == 'p')
                        fullGame.boardLogic.stones.add(new Stone(cell,Color.PINK, i, j, false));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("initial grid: ");
        fullGame.boardLogic.printGrid();
        System.out.println(fullGame.boardLogic.stones.toString());

    }

    public void updateFrame() {

        for (int i = 0; i < fullGame.boardLogic.board.length; i++) {
            for (int j = 0; j < fullGame.boardLogic.board[i].length; j++) {
                if (fullGame.boardLogic.board[i][j] == '#') {
                    buttons[i][j].setBackground(Color.BLACK);
                } else if(fullGame.boardLogic.board[i][j] == '?') {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }else{
                    buttons[i][j].setBackground(Color.WHITE);
                }

                if (fullGame.boardLogic.board[i][j] == 'R') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 10));
                } else if (fullGame.boardLogic.board[i][j] == 'G') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                } else if (fullGame.boardLogic.board[i][j] == 'B') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
                } else if (fullGame.boardLogic.board[i][j] == 'Y') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                } else if (fullGame.boardLogic.board[i][j] == 'P') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK, 10));
                }else if(fullGame.boardLogic.board[i][j]=='O'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
                } else {
//                    buttons[i][j].setBorder(null);
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }

        }
        for (Stone stone : fullGame.boardLogic.stones) {
            if (!stone.isInGoal()) {
                buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                fullGame.boardLogic = fullGame.move("UP",true);
                break;
            case KeyEvent.VK_DOWN:
                fullGame.boardLogic = fullGame.move("DOWN",true);
                break;
            case KeyEvent.VK_LEFT:
                fullGame.boardLogic = fullGame.move("LEFT",true);
                break;
            case KeyEvent.VK_RIGHT:
                fullGame.boardLogic = fullGame.move("RIGHT",true);
                break;
        }

        updateFrame();
        repaint();
        System.out.println("grid: ");
        fullGame.boardLogic.printGrid();
        for(Stone stone : fullGame.boardLogic.stones) {
            System.out.println(stone);
        }
//        for (Goal goal : fullGame.boardLogic.goals) {
//            System.out.println(goal);
//        }

        if (fullGame.boardLogic.checkGameOver()) {
            System.out.println("______________________________________");
            System.out.println("next level");
            this.dispose();
            if(index==30){
                System.out.println("all levels cleared");
                this.dispose();
                return;
            }
            index++;
            new BoardGui(index);
            return;
        }
        if (fullGame.boardLogic.lost) {
            System.out.println("______________________________________");
            System.out.println("lost , play again");
            this.dispose();
            new BoardGui(index);
        }

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
