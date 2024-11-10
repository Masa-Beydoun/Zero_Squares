import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BoardGui boardGui = new BoardGui(2);
        Algorithms algorithms = new Algorithms();
        ArrayList<State> states = algorithms.DFS(boardGui.fullGame.state);
        System.out.println(Algorithms.CYAN + "Path :" +Algorithms.RESET);
        for(State state : states) {
            System.out.println(state.toString());
            System.out.println(Algorithms.CYAN + "next move :" + Algorithms.RESET);
        }


    }
}