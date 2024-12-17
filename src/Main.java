import java.io.File;
import java.io.IOException;

public class Main {

    public static void deleteFile(String path){
        File file = new File("src\\outputs\\"+path+".txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //1 2 3 4 5 6 7 8
    public static void main(String[] args) {

//        new MainFrame(7);

        deleteFile("BFS");
        deleteFile("DFS");
        deleteFile("DFS_RECURSIVE");
        deleteFile("aStarSearch");
        deleteFile("SimpleHillClimbing");
        deleteFile("SteepestHillClimbing");
        deleteFile("UCS");


        Algorithms algorithms = new Algorithms();
        for(int i=21;i<=30;i++){
            State state = InputStates.readGrid(i);
            algorithms.startAlgorithm(AlgorithmName.BFS,state,i);
            algorithms.startAlgorithm(AlgorithmName.DFS,state,i);
            algorithms.startAlgorithm(AlgorithmName.UCS,state,i);
            algorithms.startAlgorithm(AlgorithmName.SIMPLE_HILL_CLIMBING,state,i);
            algorithms.startAlgorithm(AlgorithmName.STEEPEST_HILL_CLIMBING,state,i);
            algorithms.startAlgorithm(AlgorithmName.A_STAR,state,i);
            algorithms.startAlgorithm(AlgorithmName.A_STAR_ADVANCED,state,i);
            algorithms.initiate_DfS(state,i);

        }
    }
}