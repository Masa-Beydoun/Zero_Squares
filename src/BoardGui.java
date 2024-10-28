import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BoardGui  extends JFrame implements KeyListener {

    int gridX, gridY;
    JPanel main_panel = new JPanel();
    JButton [][]buttons;
    char [][] board;
    ArrayList<Stone> stones = new ArrayList<Stone>();
    ArrayList<Goal> goals = new ArrayList<Goal>();


    BoardGui(){
        readGrid();
        buttons = new JButton[gridX][gridY];
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

    public void readGrid(){
        String filePath = "src/grid_1";
        try  {
            FileReader fr = new FileReader(filePath);
            int character;
            int i=0,j=0;

            character = fr.read(); // x size
            gridX = Integer.parseInt(String.valueOf((char) character));
            character = fr.read(); // space

            character = fr.read(); // y size
            gridY = Integer.parseInt(String.valueOf((char) character));

            character = fr.read(); // next line
            character = fr.read(); // next line
            board= new char[gridX][gridY];


            while ((character = fr.read()) != -1){
                if((char)character == '#'){
                    board[i][j]=(char)character;
                    j++;
                }
                if((char)character == '\n'){
                    i++;
                    j=0;
                }
                if((char)character == '_'){
                    board[i][j]=(char)character;
                    j++;
                }
                if((char)character == 'r'){
                    board[i][j]=(char)character;
                    Stone stone =  Stone.builder()
                            .color(Color.RED)
                            .x(i)
                            .y(j)
                            .inGoal(false)
                            .build();
                    stones.add(stone);
                    j++;
                }
                if((char)character == 'g'){
                    board[i][j]=(char)character;
                    Stone stone =  Stone.builder()
                            .color(Color.GREEN)
                            .x(i)
                            .y(j)
                            .inGoal(false)
                            .build();
                    stones.add(stone);
                    j++;
                }
                if((char)character == 'b'){
                    board[i][j]=(char)character;
                    Stone stone =  Stone.builder()
                            .color(Color.BLUE)
                            .x(i)
                            .y(j)
                            .inGoal(false)
                            .build();
                    stones.add(stone);
                    j++;
                }
                if((char)character == 'y'){
                    board[i][j]=(char)character;
                    Stone stone =  Stone.builder()
                            .color(Color.YELLOW)
                            .x(i)
                            .y(j)
                            .inGoal(false)
                            .build();
                    stones.add(stone);
                    j++;
                }
                if((char)character == 'R'){
                    board[i][j]=(char)character;
                    Goal goal =  Goal.builder()
                            .color(Color.RED)
                            .x(i)
                            .y(j)
                            .build();
                    goals.add(goal);
                    j++;
                }
                if((char)character == 'G'){
                    board[i][j]=(char)character;
                    Goal goal =  Goal.builder()
                            .color(Color.GREEN)
                            .x(i)
                            .y(j)
                            .build();
                    goals.add(goal);
                    j++;
                }
                if((char)character == 'B'){
                    board[i][j]=(char)character;
                    Goal goal =  Goal.builder()
                            .color(Color.BLUE)
                            .x(i)
                            .y(j)
                            .build();
                    goals.add(goal);
                    j++;
                }
                if((char)character == 'Y'){
                    board[i][j]=(char)character;
                    Goal goal =  Goal.builder()
                            .color(Color.YELLOW)
                            .x(i)
                            .y(j)
                            .build();
                    goals.add(goal);
                    j++;
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void updateFrame(){

        for(int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                if(board[i][j]=='#'){
                    buttons[i][j].setBackground(Color.BLACK);
                }
                else if (board[i][j]=='r'){
                    buttons[i][j].setBackground(Color.RED);
                }
                else if (board[i][j]=='g'){
                    buttons[i][j].setBackground(Color.GREEN);
                }
                else if (board[i][j]=='b'){
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else if (board[i][j]=='y'){
                    buttons[i][j].setBackground(Color.YELLOW);
                }
                else {
                    buttons[i][j].setBackground(Color.WHITE);
                }
                if (board[i][j]=='R'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 10));
                }
                else if (board[i][j]=='G'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                }
                else if (board[i][j]=='B'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
                }else if (board[i][j]=='Y'){
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
                }
                else{
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
                }
            }
        }
    }




    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                move("UP");
                break;
            case KeyEvent.VK_DOWN:
                move("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                move("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                move("RIGHT");
                break;
        }
        repaint();
        for(Stone stone: stones){
            System.out.println(stone.toString());
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }



    public void move(String dir){
        for(int i=0;i<stones.size();i++) {

            Stone s = stones.get(i);
            if(s.isInGoal()) continue;
            char c = board[s.getX()][s.getY()];
            System.out.println("char "+c);
            board[s.getX()][s.getY()]='_';

            if (dir == "UP") {
                int temp = s.getX();
                while(temp>=0 && board[temp][s.getY()]!='#'){
                    if(board[temp][s.getY()]==Character.toUpperCase(c)) {
                        s.setInGoal(true);
                        board[temp][s.getY()]='_';
                        break;
                    }
                    temp--;
                }
                stones.get(i).setX(temp+1);
            } else if (dir == "DOWN") {
                int temp = s.getX();
                while(temp<gridX && board[temp][s.getY()]!='#'){
                    if(board[temp][s.getY()]==Character.toUpperCase(c)) {
                        board[temp][s.getY()]='_';
                        s.setInGoal(true);
                        break;
                    }
                    temp++;
                }
                stones.get(i).setX(temp-1);
            } else if (dir == "LEFT") {
                int temp = s.getY();
                System.out.println("cell in board " + board[s.getX()][s.getY()]);
                while(temp>=0 && board[s.getX()][temp]!='#'){
                    if(board[s.getX()][temp]==Character.toUpperCase(c)){
                        s.setInGoal(true);
                        board[s.getX()][temp]='_';
                        break;
                    }
                    temp--;
                }
                stones.get(i).setY(temp+1);
            } else {
                int temp = s.getY();
                while(temp<gridY && board[s.getX()][temp]!='#'){
                    if(board[s.getX()][temp]==Character.toUpperCase(c)){
                        s.setInGoal(true);
                        board[s.getX()][temp]='_';
                        break;
                    }
                    temp++;
                }
                stones.get(i).setY(temp-1);
            }
            if(!s.isInGoal()){
                board[s.getX()][s.getY()]=c;
            }

        }
        updateFrame();

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
