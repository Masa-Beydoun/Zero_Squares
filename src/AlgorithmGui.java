import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AlgorithmGui extends JFrame {

    int index;
    State state;
    JPanel mainPanel = new JPanel();
    JPanel panel = new JPanel();
    JButton[][] buttons;

    AlgorithmGui(int index, String alg) {
        this.index = index;
        state = InputStates.readGrid(index);

        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(state.board.length, state.board[0].length));

        buttons = new JButton[state.board.length][state.board[0].length];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
                buttons[i][j].setBackground(Color.WHITE);
                panel.add(buttons[i][j]);
            }
        }

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 8));

        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
        this.setFocusable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        SwingUtilities.invokeLater(() -> updateFullFrame(alg,index));
    }

    public void updateFullFrame(String alg,int index) {
        ArrayList<State> path = new ArrayList<>();
        Algorithms algorithms = new Algorithms();

        if (alg.equals("BFS")) {
            System.out.println("in bfs");
            path = algorithms.startAlgorithm(AlgorithmName.BFS,state,index);
        }
        if (alg.equals("DFS")) {
            path = algorithms.startAlgorithm(AlgorithmName.DFS,state,index);
        }
        if(alg.equals("UCS")) {
            path = algorithms.startAlgorithm(AlgorithmName.UCS,state,index);
        }
        if (alg.equals("DFS_RECURSIVE")) {
             algorithms.initiate_DfS(state,index);
             path=algorithms.globalPath;
        }
        if (alg.equals("AStar")) {
            path = algorithms.startAlgorithm(AlgorithmName.A_STAR,state,index);
        }
        if (alg.equals("simpleHillClimbing")) {
            path = algorithms.startAlgorithm(AlgorithmName.SIMPLE_HILL_CLIMBING,state,index);
        }
        if (alg.equals("steepestHillClimbing")) {
            path = algorithms.startAlgorithm(AlgorithmName.STEEPEST_HILL_CLIMBING,state,index);
        }
        if (alg.equals("advancedAStar")) {
            path = algorithms.startAlgorithm(AlgorithmName.A_STAR_ADVANCED,state,index);
        }
        if (path != null && path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        ArrayList<State> finalPath = path;
        Timer timer = new Timer(250, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (finalPath != null && step < finalPath.size()) {
                    State currentState = finalPath.get(step);
                    updateFrame(buttons, currentState);
                    repaint();
                    step++;
                } else {
                    ((Timer) e.getSource()).stop();
                    System.out.println("Path animation completed.");
                    System.out.println();
                    System.out.println();
                    System.out.println("Next level");
                    System.out.println();
                    System.out.println();

                    dispose();
                    if (AlgorithmGui.this.index == 30) {
                        System.out.println("All levels cleared");
                        return;
                    }
                    AlgorithmGui.this.index++;
                    new MainFrame(AlgorithmGui.this.index);
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    public static void updateFrame(JButton[][] buttons, State state) {
        if(state.lost) {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    buttons[i][j].setBackground(Color.GRAY);
                }
            }
            return;
        }
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (state.board[i][j] == '#') {
                    buttons[i][j].setBackground(Color.BLACK);
                } else if (state.board[i][j] == '?') {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
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
                } else if (state.board[i][j] == 'O') {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
                } else {
                    buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        }

        for (Stone stone : state.stones) {
            if (!stone.isInGoal()) {
                if(stone.getC()=='r')
                    buttons[stone.getX()][stone.getY()].setBackground(Color.RED);
                if(stone.getC()=='y')
                    buttons[stone.getX()][stone.getY()].setBackground(Color.YELLOW);
                if(stone.getC()=='g')
                    buttons[stone.getX()][stone.getY()].setBackground(Color.GREEN);
                if(stone.getC()=='p')
                    buttons[stone.getX()][stone.getY()].setBackground(Color.PINK);
                if(stone.getC()=='b')
                    buttons[stone.getX()][stone.getY()].setBackground(Color.blue);
            }
        }
    }
}
