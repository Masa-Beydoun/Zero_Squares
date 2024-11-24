import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JButton user = new JButton("User");
    JButton BFS = new JButton("BFS");
    JButton DFS = new JButton("DFS");
    JButton DFS_RESURSIVE = new JButton("DFS_RECURSIVE");
    JButton UCS = new JButton("UCS");
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

        user.setFocusable(false);
        BFS.setFocusable(false);
        DFS.setFocusable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.GRAY);
        this.add(user);
        this.add(BFS);
        this.add(DFS);
        this.add(UCS);
        this.add(DFS_RESURSIVE);
        this.setSize(new Dimension(700,300));
        this.setVisible(true);
    }

}
