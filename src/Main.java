import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BoardGui boardGui = new BoardGui(2);
        Algorithms algorithms = new Algorithms();
        algorithms.DFS(boardGui.state);
        algorithms.BFS(boardGui.state);



    }
}