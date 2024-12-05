import java.io.File;

public class Main {

    public static void deleteFile(String path){
        File file = new File("src/outputs"+path+".txt");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {

//        new MainFrame(1);

        deleteFile("BFS");
        deleteFile("DFS");
        deleteFile("DFS_RECURSIVE.txt");
        deleteFile("aStarSearch.txt");
        deleteFile("SimpleHillClimbing.txt");
        deleteFile("SteepestHillClimbing.txt.txt");
        deleteFile("UCS.txt.txt");


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