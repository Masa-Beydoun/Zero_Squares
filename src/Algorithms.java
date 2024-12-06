import java.io.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


import static java.util.Collections.asLifoQueue;
import static java.util.Collections.reverse;

public class Algorithms {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
//    public static final String WHITE = "\u001B[37m";
//    public static final String BLACK = "\u001B[40m";

    public ArrayList<State> startAlgorithm(AlgorithmName alg,State root,int index){
        ArrayList<State>visited = new ArrayList<>();
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();
        root.parent=null;
        ArrayList<State> path=null;
        if(alg==AlgorithmName.DFS){
            path = DFS(visited,root);
        }
        else if(alg==AlgorithmName.BFS){
            path = BFS(visited,root);
        } else if (alg==AlgorithmName.UCS) {
            path = UCS(visited,root);
        } else if (alg==AlgorithmName.SIMPLE_HILL_CLIMBING) {
            path = simpleHillClimbing(visited,root);
        } else if (alg==AlgorithmName.STEEPEST_HILL_CLIMBING) {
            path = simpleHillClimbing(visited,root);
        }else if(alg == AlgorithmName.A_STAR){
            path= AStarSearch(visited,root);
        }else if (alg==AlgorithmName.A_STAR_ADVANCED) {
            path = advancedAStarSearch(visited,root);
        }
        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;
        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

        long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryUsedAfter - memoryUsedBefore;
        writeLogToFile(index,alg,visited,getPath(root,visited),durationInMillis,memoryUsed);
        return path;
    }

    public ArrayList<State> DFS(ArrayList<State>visitedStates,State root){
        Stack<State> stack = new Stack<>();
        stack.add(root);
        while(!stack.isEmpty()){
            State currentState = stack.pop();
            if(existInVisited(currentState,visitedStates)) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState,visitedStates);
            }
            ArrayList<State>nextStates =  currentState.possibleBoards();
            for(State nextState : nextStates){
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                stack.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }
    public void initiate_DfS(State root,int index){
        ArrayList<State> visitedStates = new ArrayList<>();
        DFS_Recursive(root,visitedStates,index);
    }
    ArrayList<State> globalPath = new ArrayList<>();
    public boolean DFS_Recursive(State root,ArrayList<State> visitedStates,int index){
        if(checkGoal(root)) {
            System.out.println("DFS Recursive");
            globalPath = getPath(root,visitedStates);
            writeLogToFile(index,AlgorithmName.DFS_RECURSIVE,visitedStates,getPath(root,visitedStates),0,0);
            return true;
        }
        boolean flag = false;
        if (existInVisited(root, visitedStates)) return false;

        visitedStates.add(root);
        root.possibleBoards();
        ArrayList<State> nextStates =  root.possibleBoards();
        for(State nextState : nextStates){
            if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
            nextState.parent = root;
            if(DFS_Recursive(nextState,visitedStates,index)) return true;
        }
        return false;
    }
    public ArrayList<State> UCS(ArrayList<State> visitedStates,State root) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s->s.calculateCost()));
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (existInVisited(currentState, visitedStates)) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState,visitedStates);
            }
            currentState.possibleBoards();
            ArrayList<State> nextStates =  currentState.possibleBoards();
            for (State nextState : nextStates) {
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                nextState.cost = currentState.cost+nextState.calculateCost();
                nextState.parent = currentState;
                queue.add(nextState);
            }
        }
        return null;
    }
    public ArrayList<State> BFS(ArrayList<State>visitedStates,State root){
        Queue<State> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()){
            State currentState = queue.remove();
            if(existInVisited(currentState,visitedStates)) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState,visitedStates);
            }
            ArrayList<State> nextStates =  currentState.possibleBoards();
            for(State nextState : nextStates){
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                queue.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }
    public ArrayList<State> steepestHillClimbing(ArrayList<State>visitedStates,State root) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic()));
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            if(existInVisited(currentState, visitedStates)) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                if(nextState.heuristic()>currentState.heuristic()) continue;
                queue.add(nextState);
                nextState.cost=currentState.cost+nextState.calculateCost();
                nextState.parent = currentState;
            }
        }
        return null;
    }
    public ArrayList<State> simpleHillClimbing(ArrayList<State> visitedStates ,State root){
        Queue<State> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            boolean foundLess=false;
            for (State nextState : nextStates) {
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                if(nextState.heuristic()>currentState.heuristic()) continue;
                foundLess=true;
                nextState.parent = currentState;
                queue.add(nextState);
            }
            if(!foundLess) return null;
        }
        return null;
    }
    public ArrayList<State> AStarSearch(ArrayList<State> visitedStates ,State root) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic()));
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                nextState.parent = currentState;
                nextState.cost = currentState.cost+nextState.calculateCost();
                queue.add(nextState);
            }
        }
        return null;
    }
    public ArrayList<State> advancedAStarSearch(ArrayList<State> visitedStates ,State root) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.advancedHeuristic()));
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            if(existInVisited(currentState, visitedStates)) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                if(nextState == null || nextState.checkLost() || existInVisited(nextState, visitedStates)) continue;
                nextState.parent = currentState;
                nextState.cost = currentState.cost+nextState.calculateCost();
                queue.add(nextState);
            }
        }
        return null;
    }
    public ArrayList<State> getPath(State root,ArrayList<State> visitedStates){
        ArrayList<State> path = new ArrayList<>();
        path.add(root);
        while (root.parent != null) {
            path.add(root.parent);
            root = root.parent;
        }
        reverse(path);
        return printPath(visitedStates, path);
    }
    public boolean checkGoal(State root){
        for(Stone stone : root.stones){
            if(!stone.isInGoal())
                return false;
        }
        return true;
    }
    public void writeLogToFile(int level, AlgorithmName algo, ArrayList<State> visitedStates, ArrayList<State> path, long time, long memory) {
        String textToAdd = "level: " + level + "\n" +
                "algorithm: " + algo.name() + "\n" +
                "number of visited states: " + visitedStates.size() + "\n" +
                "number of states in the path: " + path.size() + "\n" +
                "time: " + time + " ms\n" +
                "memory: " + memory + " bytes\n\n";

        String fileName = "src/outputs/"+algo.name() + ".txt";

        try (FileWriter writer = new FileWriter(fileName, true)) { // Append to the file
            writer.write(textToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<State> printPath(ArrayList<State> visitedStates, ArrayList<State> path) {

        System.out.println(Algorithms.PURPLE + "number of visited states: " + visitedStates.size() + Algorithms.RESET);
        System.out.println(Algorithms.PURPLE + "number of states in the path: " + path  .size() + Algorithms.RESET);
        System.out.println(Algorithms.CYAN + "Path :" +Algorithms.RESET);
        int i=1;
        for(State state : path) {
            System.out.println(state.toString());
            System.out.println(Algorithms.CYAN + "next move number " + i +"  " + Algorithms.RESET);
            i++;
        }
        System.out.println(Algorithms.RED + "End of path ." + Algorithms.RESET);
        return path;
    }
    private boolean existInVisited(State nextState, ArrayList<State> visitedStates) {
        boolean flag = false;
        for(State visitedState : visitedStates){
            if(visitedState.equals(nextState)) {
                flag= true;
                break;
            }
        }
        return flag;
    }


}
