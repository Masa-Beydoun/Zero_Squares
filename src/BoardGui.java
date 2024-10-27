import javax.swing.*;
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
    int [][] board;
    ArrayList<Stone> stones = new ArrayList<Stone>();


    BoardGui(){
        initiateBoard();
        buttons = new JButton[gridX][gridY];

        main_panel.setLayout(new GridLayout(buttons.length,buttons[0].length));
        for(int i=0;i<buttons.length;i++){
            for(int j=0;j<buttons[i].length;j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
            }
        }

        for(int i=0;i<buttons.length;i++){
            for(int j=0;j<buttons[i].length;j++){
                main_panel.add(buttons[i][j]);
            }
        }
        main_panel.setSize(new Dimension(700,700));
        this.add(main_panel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);


    }

    public void initiateBoard(){
        readGrid();
        Stone stone =  Stone.builder()
                .color(Color.RED)
                .x(7)
                .y(7)
                .inGoal(false)
                .build();
        stones.add(stone);

    }

    public void readGrid(){
        String filePath = "src/grid_1";
        try  {
            FileReader fr = new FileReader(filePath);
            int character;
            int i=0,j=0;

            character = fr.read(); // x size
            gridX = Integer.parseInt(String.valueOf((char) character));
            System.out.println(gridX);
            character = fr.read(); // space

            character = fr.read(); // y size
            gridY = Integer.parseInt(String.valueOf((char) character));
            System.out.println(gridY);

            character = fr.read(); // next line
            character = fr.read(); // next line
            System.out.print((char) character);
            board= new int[gridX][gridY];


            while ((character = fr.read()) != -1){
                System.out.print((char) character);
                if((char)character == '1'){
                    board[i][j]=1;
                    i++;
                }
                if((char)character == '\n'){
                    System.out.println( "   " + j);
                    j++;
                    i=0;
                }
                if((char)character == '0'){
                    board[i][j]=0;
                    i++;
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("up");
                move("UP");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                move("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                move("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                move("RIGHT");
                break;
        }
        repaint();
    }



    public void move(String dir){
        for(int i=0;i<stones.size();i++) {
            Stone s = stones.get(i);
            if (dir == "UP") {
                int temp = s.getX();
                while(temp>=0 && board[temp][s.getY()]!=1){
                    temp--;
                }
                stones.get(i).setX(temp+1);
            } else if (dir == "DOWN") {
                int temp = s.getX();
                while(temp<gridX && board[temp][s.getY()]!=1){
                    temp++;
                }
                stones.get(i).setX(temp-1);

            } else if (dir == "LEFT") {
                int temp = s.getY();
                while(temp>=0 && board[s.getX()][temp]!=1){
                    temp--;
                }
                stones.get(i).setY(temp+1);
            } else {
                int temp = s.getY();
                while(temp<gridY && board[s.getX()][temp]!=1){
                    temp++;
                }
                stones.get(i).setY(temp-1);
            }
        }
        updateFrame();

    }

    public void updateFrame(){
        readGrid();
        for(Stone stone:stones){
            buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
        }
        for(int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                if(board[i][j]==1){
                    buttons[i][j].setBackground(Color.BLACK);
                }
                else if(board[i][j]==0){
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
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
