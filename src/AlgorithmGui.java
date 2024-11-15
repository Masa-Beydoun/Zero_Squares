import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AlgorithmGui extends JFrame {

    int index;
    State state = new State();
    JPanel mainPanel = new JPanel();
    JPanel panel = new JPanel();
    JButton[][] buttons;

    AlgorithmGui(int index, String alg) {
        this.index = index;
        state = InputStates.readGrid(index);

        // Setup frame layout and properties
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the main panel and grid panel
        mainPanel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(state.gridX, state.gridY));

        // Initialize buttons and add them to the grid panel
        buttons = new JButton[state.gridX][state.gridY];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusable(false);
                buttons[i][j].setBackground(Color.WHITE);
                panel.add(buttons[i][j]);
            }
        }

        // Add the grid panel to the main panel and set borders
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 8));

        // Add the main panel to the frame
        this.add(mainPanel, BorderLayout.CENTER);

        // Make the frame visible after components are added
        this.setVisible(true);
        this.setFocusable(true);

        // Call the method to update the frame
        updateFullFrame(alg);
    }

    public void updateFullFrame(String alg) {
        ArrayList<State> path = new ArrayList<>();
        Algorithms algorithms = new Algorithms();

        // Get the path using the algorithm (BFS or DFS)
        if (alg.equals("BFS")) {
            path = algorithms.BFS(state);
        } else if (alg.equals("DFS")) {
            path = algorithms.DFS(state);
        }

        // Ensure the path is not empty
        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        ArrayList<State> finalPath = path;
        Timer timer = new Timer(500, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step < finalPath.size()) {
                    State currentState = finalPath.get(step);
                    updateFrame(buttons, currentState);  // Update frame with the current state
                    repaint();  // Refresh the frame to show the new state
                    step++;
                } else {
                    ((Timer) e.getSource()).stop();  // Stop the timer when the path is completed
                    System.out.println("Path animation completed.");
                    System.out.println("Next level");
                    dispose();
                    if (index == 30) {
                        System.out.println("All levels cleared");
                        return;
                    }
                    index++;
                    new MainFrame(index);
                }
            }
        });

        timer.setInitialDelay(0);  // Start immediately
        timer.start();  // Start the timer
    }

    public static void updateFrame(JButton[][] buttons, State state) {
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
                buttons[stone.getX()][stone.getY()].setBackground(stone.getColor());
            }
        }
    }
}
