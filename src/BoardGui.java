import javax.swing.*;
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
    JPanel firstMainPanel = new JPanel();
    JPanel secondMainPanel = new  JPanel();
    JPanel thirdMainPanel = new  JPanel();
    JPanel fourthMainPanel= new JPanel();
    JPanel fifthMainPanel= new JPanel();
    JPanel firstPanel= new JPanel();
    JPanel secondPanel= new JPanel();
    JPanel thirdPanel= new JPanel();
    JPanel fourthPanel = new JPanel();
    JPanel fifthPanel= new JPanel();
    JButton[][] firstButton,secondButton,thirdButton, fourthButton,fifthButton;
    JLabel firstLabel = new JLabel();
    JLabel secondLabel = new JLabel();
    JLabel thirdLabel = new JLabel();
    JLabel fourthLabel = new JLabel();
    JLabel fifthLabel = new JLabel();



    BoardGui(int index) {
        this.index = index;
        readGrid(index);
        this.setLayout(new GridLayout(1, 5));


        firstButton = new JButton[fullGame.state.gridX][fullGame.state.gridY];
        secondButton = new JButton[fullGame.state.gridX][fullGame.state.gridY];
        thirdButton = new JButton[fullGame.state.gridX][fullGame.state.gridY];
        fourthButton = new JButton[fullGame.state.gridX][fullGame.state.gridY];
        fifthButton = new JButton[fullGame.state.gridX][fullGame.state.gridY];


        createOnePanel(firstMainPanel,firstPanel,firstButton,firstLabel,"current");
        createOnePanel(secondMainPanel,secondPanel,secondButton,secondLabel,"UP");
        createOnePanel(thirdMainPanel,thirdPanel,thirdButton,thirdLabel,"DOWN");
        createOnePanel(fourthMainPanel,fourthPanel,fourthButton,fourthLabel,"LEFT");
        createOnePanel(fifthMainPanel,fifthPanel,fifthButton,fifthLabel,"RIGHT");


        firstMainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,8));
        secondMainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,8));
        thirdMainPanel.setBorder(BorderFactory.createLineBorder(Color.PINK,8));
        fourthMainPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,8));
        fifthMainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,8));

        this.add(firstMainPanel);
        this.add(secondMainPanel);
        this.add(thirdMainPanel);
        this.add(fourthMainPanel);
        this.add(fifthMainPanel);


        updateFullFrame();
        this.setSize(new Dimension(1400,700));

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
            fullGame.state.gridX = Integer.parseInt(dimensions[0]);
            fullGame.state.gridY = Integer.parseInt(dimensions[1]);

            // Initialize the board
            fullGame.state.board = new char[fullGame.state.gridX][fullGame.state.gridY];

            for (int i = 0; i < fullGame.state.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < fullGame.state.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    fullGame.state.board[i][j] = cell;
                    if (cell=='R')
                        fullGame.state.goals.add(new Goal(cell,i, j,Color.RED));
                    if(cell== 'G')
                        fullGame.state.goals.add(new Goal(cell,i, j,Color.GREEN));
                    if(cell== 'B')
                        fullGame.state.goals.add(new Goal(cell,i, j,Color.BLUE));
                    if(cell== 'Y')
                        fullGame.state.goals.add(new Goal(cell,i, j,Color.YELLOW));
                    if(cell == 'P')
                        fullGame.state.goals.add(new Goal(cell,i,j,Color.PINK));
                    if(cell == '?')
                        fullGame.state.goals.add(new Goal(cell,i,j,Color.DARK_GRAY));
                }
            }
            for (int i = 0; i < fullGame.state.gridX; i++) {
                String line = br.readLine();
                String[] cells = line.split(" ");
                for (int j = 0; j < fullGame.state.gridY; j++) {
                    char cell = cells[j].charAt(0);
                    if (cell=='r')
                            fullGame.state.stones.add(new Stone(cell,Color.RED, i, j, false));
                    if (cell=='g')
                        fullGame.state.stones.add(new Stone(cell,Color.GREEN, i, j, false));
                    if (cell=='b')
                        fullGame.state.stones.add(new Stone(cell,Color.BLUE, i, j, false));
                    if (cell=='y')
                        fullGame.state.stones.add(new Stone(cell,Color.YELLOW, i, j, false));
                    if(cell == 'p')
                        fullGame.state.stones.add(new Stone(cell,Color.PINK, i, j, false));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("initial grid: ");
        fullGame.state.printGrid();
        System.out.println(fullGame.state.stones.toString());

    }
    public void createOnePanel(JPanel mainPanel,JPanel buttonPanel,JButton[][] buttons,JLabel jLabel,String str){
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(fullGame.state.gridX,fullGame.state.gridY));
        jLabel.setText(str);
        jLabel.setHorizontalAlignment(JLabel.CENTER);  // Ensure text is centered
        mainPanel.add(jLabel, BorderLayout.NORTH);
        buttonPanel.setLayout(new GridLayout(buttons.length, buttons[0].length));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
            }
        }
        for (int i = 0; i < buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++) {
                buttonPanel.add(buttons[i][j]);
            }
        }
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    public void updateFullFrame(){
        updateFrame(firstButton,fullGame.state);

        ArrayList<State> nextSt= fullGame.state.possibleBoards();
        if(!nextSt.isEmpty()) {
            System.out.println("full states");
            updateFrame(secondButton, nextSt.get(0));
            updateFrame(thirdButton, nextSt.get(1));
            updateFrame(fourthButton,nextSt.get(2));
            updateFrame(fifthButton, nextSt.get(3));
        }
    }

    public void updateFrame(JButton[][] buttons,State state) {

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (state.board[i][j] == '#') {
                    buttons[i][j].setBackground(Color.BLACK);
                } else if(state.board[i][j] == '?') {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                }else{
                    buttons[i][j].setBackground(Color.WHITE);
                }

                if (state.board[i][j] == 'R') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 10));
                } else if (state.board[i][j] == 'G') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                } else if (state.board[i][j] == 'B') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
                } else if (state.board[i][j] == 'Y') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                } else if (state.board[i][j] == 'P') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK, 10));
                }else if(state.board[i][j]=='O'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
                } else {
//                    jButtons[i][j].setBorder(null);
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }

        }
        for (Stone stone : state.stones) {
            if (!stone.isInGoal()) {
                buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                 fullGame.move("UP",true);
                break;
            case KeyEvent.VK_DOWN:
                fullGame.move("DOWN",true);
                break;
            case KeyEvent.VK_LEFT:
                fullGame.move("LEFT",true);
                break;
            case KeyEvent.VK_RIGHT:
                fullGame.move("RIGHT",true);
                break;
        }

        updateFullFrame();
        repaint();
        System.out.println("grid: ");
        fullGame.state.printGrid();
        for(Stone stone : fullGame.state.stones) {
            System.out.println(stone);
        }
//        for (Goal goal : fullGame.boardLogic.goals) {
//            System.out.println(goal);
//        }

        if (fullGame.state.checkGameOver()) {
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
        if (fullGame.state.lost) {
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
