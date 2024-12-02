import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class BoardGui  extends JFrame implements KeyListener{

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
        state = InputStates.readGrid(index);
        this.setLayout(new GridLayout(1, 5));


        firstButton = new JButton[state.board.length][state.board[0].length];
        secondButton = new JButton[state.board.length][state.board[0].length];
        thirdButton = new JButton[state.board.length][state.board[0].length];
        fourthButton = new JButton[state.board.length][state.board[0].length];
        fifthButton = new JButton[state.board.length][state.board[0].length];


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


    public void createOnePanel(JPanel mainPanel,JPanel buttonPanel,JButton[][] buttons,JLabel jLabel,String str){
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(state.board.length,state.board[0].length));
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

        ArrayList<State> nextStates =  state.possibleBoards();


        if(!nextStates.isEmpty()) {

            if(nextStates.get(0)!= null)
                updateFrame(secondButton, nextStates.get(0));

            if(nextStates.get(1)!= null)
                updateFrame(thirdButton, nextStates.get(1));

            if(nextStates.get(2)!= null)
                updateFrame(fourthButton, nextStates.get(2));

            if(nextStates.get(3)!= null)
                updateFrame(fifthButton, nextStates.get(3));


        }
    }

    public void updateFrame(JButton[][] buttons,State state) {

        if(state.lost) return;
        AlgorithmGui.updateFrame(buttons, state);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("UP");
                 state = state.move("UP");
                break;
            case KeyEvent.VK_DOWN:
                state = state.move("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                state = state.move("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                state = state.move("RIGHT");
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
            new MainFrame(index);
            return;
        }
        if (state.lost) {
            System.out.println("______________________________________");
            System.out.println("lost , play again");
            this.dispose();
            new MainFrame(index);
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
