import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JButton user = new JButton("User");
    JButton BFS = new JButton("BFS");
    JButton DFS = new JButton("DFS");
    JButton DFS_RESURSIVE = new JButton("DFS_RECURSIVE");
    JButton UCS = new JButton("UCS");
    JButton AStar = new JButton("A star");
    JButton simpleHillClimbing = new JButton("Simple hill climbing");
    JButton steepestHillClimbing = new JButton("steepest hill climbing");
    JButton advancedAStar = new JButton("advancedAStar");
    public MainFrame(int index) {
        this.setLayout(new GridLayout(1,3));
        user.addActionListener(e -> {
            this.dispose();
            new BoardGui(index);
        });
        BFS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"BFS");
        });
        DFS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"DFS");
        });
        UCS.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"UCS");
        });
        DFS_RESURSIVE.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"DFS_RECURSIVE");
        });
        AStar.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"AStar");
        });
        simpleHillClimbing.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"simpleHillClimbing");
        });
        steepestHillClimbing.addActionListener(e -> {
            this.dispose();
            new AlgorithmGui(index,"steepestHillClimbing");
        });
        advancedAStar.addActionListener(e -> {
                this.dispose();
            new AlgorithmGui(index,"advancedAStar");
        });

        user.setFocusable(false);
        BFS.setFocusable(false);
        DFS.setFocusable(false);
        DFS_RESURSIVE.setFocusable(false);
        AStar.setFocusable(false);
        advancedAStar.setFocusable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.GRAY);
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
        this.setSize(new Dimension(700,300));
        this.setVisible(true);
    }

}
