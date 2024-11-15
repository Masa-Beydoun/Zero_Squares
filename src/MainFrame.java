import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JButton user = new JButton("User");
    JButton BFS = new JButton("BFS");
    JButton DFS = new JButton("DFS");
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
            new AlgorithmGui(index,"BFS");
        });

        this.setBackground(Color.GRAY);
        this.add(user);
        this.add(BFS);
        this.add(DFS);
        this.setSize(new Dimension(700,300));
        this.setVisible(true);
    }

}
