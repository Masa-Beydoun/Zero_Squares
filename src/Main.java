import java.io.File;

public class Main {

    public static void deleteFile(String path){
        File file = new File("src/outputs"+path+".txt");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {

//        new MainFrame(30);

        deleteFile("BFS");
        deleteFile("DFS");
        deleteFile("DFS_RECURSIVE.txt");
        deleteFile("aStarSearch.txt");
        deleteFile("SimpleHillClimbing.txt");
        deleteFile("SteepestHillClimbing.txt.txt");
        deleteFile("UCS.txt.txt");

//
        Algorithms algorithms = new Algorithms();
        for(int i=1;i<=30;i++){
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