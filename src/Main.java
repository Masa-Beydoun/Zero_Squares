import java.io.File;

public class Main {
    public static void main(String[] args) {

//        new MainFrame(1);



        File file = new File("output.txt");
        if (file.exists()) {
            file.delete();
        }
        Algorithms algorithms = new Algorithms();
        for(int i=1;i<=30;i++){
            State state = InputStates.readGrid(i);
            algorithms.BFS(state,i);
            System.out.println("BFS done");
            algorithms.DFS(state,i);
            System.out.println("DFS done");

            algorithms.initiate_DfS(state,i);
            algorithms.UCS(state,i);
            System.out.println("UCS done");

            algorithms.simpleHillClimbing(state,i);
            algorithms.steepestHillClimbing(state,i);
            algorithms.AStarSearch(state,i);

        }
    }
}