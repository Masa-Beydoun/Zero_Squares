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
    State state=new State();
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


        firstButton = new JButton[state.gridX][state.gridY];
        secondButton = new JButton[state.gridX][state.gridY];
        thirdButton = new JButton[state.gridX][state.gridY];
        fourthButton = new JButton[state.gridX][state.gridY];
        fifthButton = new JButton[state.gridX][state.gridY];


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
        this.setSize(new Dimension(1600,800));
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
                        state.goals.add(new Goal(cell,i, j,Color.RED));
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

    }
    public void createOnePanel(JPanel mainPanel,JPanel buttonPanel,JButton[][] buttons,JLabel jLabel,String str){
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(state.gridX,state.gridY));
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
        updateFrame(firstButton,state);

        state.possibleBoards();
//        System.out.println("after states");
        if(!state.nextStates.isEmpty()) {
            updateFrame(secondButton, state.nextStates.get(0));
            updateFrame(thirdButton, state.nextStates.get(1));
            updateFrame(fourthButton,state.nextStates.get(2));
            updateFrame(fifthButton, state.nextStates.get(3));
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
                 state.move("UP");
                break;
            case KeyEvent.VK_DOWN:
                state.move("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                state.move("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                state.move("RIGHT");
                break;
        }

        updateFullFrame();
        repaint();


        if (state.checkGameOver()) {
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
        if (state.lost) {
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
