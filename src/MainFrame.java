import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    int index;
    JLabel level;
    JButton user = new JButton("User");
    JButton BFS = new JButton("BFS");
    JButton DFS = new JButton("DFS");
    JButton DFS_RESURSIVE = new JButton("DFS_RECURSIVE");
    JButton UCS = new JButton("UCS");
    JButton AStar = new JButton("A star");
    JButton simpleHillClimbing = new JButton("Simple hill climbing");
    JButton steepestHillClimbing = new JButton("steepest hill climbing");
    JButton advancedAStar = new JButton("advancedAStar");
    JButton nextStep = new JButton("Next Level");


    //1


    public MainFrame(int index) {
        this.index = index;
        this.setLayout(new GridLayout(1,3));
        level = new JLabel("Level: " + index);
        user.addActionListener(e -> {
            this.dispose();
            new BoardGui(index);
        });
        BFS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.BFS);
        });
        DFS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.DFS);
        });
        nextStep.addActionListener(e -> {
            this.dispose();
            this.index++;
            if (this.index == 30) {
                System.out.println("All levels cleared");
                return;
            }
            new MainFrame(this.index);
        });
        UCS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.UCS);
        });
        DFS_RESURSIVE.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.DFS_RECURSIVE);
        });
        AStar.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.A_STAR);
        });
        simpleHillClimbing.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.SIMPLE_HILL_CLIMBING);
        });
        steepestHillClimbing.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,AlgorithmName.STEEPEST_HILL_CLIMBING);
        });
        advancedAStar.addActionListener(e -> {
                this.dispose();
            new AlgorithmGui(index,AlgorithmName.A_STAR_ADVANCED);
        });

        user.setFocusable(false);
        BFS.setFocusable(false);
        DFS.setFocusable(false);
        DFS_RESURSIVE.setFocusable(false);
        AStar.setFocusable(false);
        advancedAStar.setFocusable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.GRAY);
//        this.setLayout(null);
        this.add(level);
        this.add(user);
        this.add(BFS);
        this.add(DFS);
        this.add(UCS);
        this.add(DFS_RESURSIVE);
        this.add(AStar);
        this.add(simpleHillClimbing);
        this.add(steepestHillClimbing);
        this.add(AStar);
        this.add(advancedAStar);
        this.add(nextStep);
        this.setSize(new Dimension(1300,300));
        this.setVisible(true);
    }

}
