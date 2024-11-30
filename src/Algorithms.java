import java.util.*;

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



    public ArrayList<State> DFS(State root){
        Set<State> visitedStates = new HashSet<>();
        Stack<State> stack = new Stack<>();
        root.parent = null;
        stack.add(root);
        while(!stack.isEmpty()){
            System.out.println("number of visits: " + visitedStates.size());
            State currentState = stack.pop();
            if (checkGoal(currentState)) {
                System.out.println("DFS");
                return getPath(currentState,visitedStates);
            }
            visitedStates.add(currentState);
            currentState.possibleBoards();
            for(State nextState : currentState.nextStates){
                if (nextState == null || visitedStates.contains(nextState)) continue;
                stack.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }

    public void initiate_DfS(State root){
        Set<State> visitedStates = new HashSet<>();
        DFS_Recursive(root,visitedStates);
    }

    ArrayList<State> globalPath = new ArrayList<>();
    public boolean DFS_Recursive(State root,Set<State> visitedStates){
        if(checkGoal(root)) {
            System.out.println("DFS Recursive");
            globalPath = getPath(root,visitedStates);
            return true;
        }
        visitedStates.add(root);
        root.possibleBoards();
        for(State nextState : root.nextStates){
            if (nextState == null || visitedStates.contains(nextState)) continue;
            nextState.parent = root;
            boolean flag = DFS_Recursive(nextState,visitedStates);
            if(flag) return true;

        }
        return false;

    }


    public ArrayList<State> UCS(State root) {
        HashSet<State> visitedStates = new HashSet<>();
        PriorityQueue<State> queue = new PriorityQueue<>();
        root.parent = null;
        root.cost = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            System.out.println("number of visited states: " + visitedStates.size());
            State currentState = queue.poll();
            if (checkGoal(currentState)) {
                System.out.println("UCS");
                return getPath(currentState,visitedStates);
            }
            visitedStates.add(currentState);
            currentState.possibleBoards();
            for (State nextState : currentState.nextStates) {
                if (nextState == null || visitedStates.contains(nextState)) continue;
                nextState.cost = currentState.cost + 1;
                nextState.parent = currentState;
                queue.add(nextState);
            }
        }
        return null;
    }

    public ArrayList<State> BFS(State root){
        Set<State> visitedStates = new HashSet<>();
        Queue<State> queue = new ArrayDeque<>();
        root.parent = null;
        queue.add(root);
        while(!queue.isEmpty()){
            System.out.println("number of visits: " + visitedStates.size());
            State currentState = queue.remove();
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("BFS");
                return getPath(currentState,visitedStates);
            }
            currentState.possibleBoards();
            for(State nextState : currentState.nextStates){
                if (nextState == null || visitedStates.contains(nextState)) continue;
                queue.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }

    public ArrayList<State> getPath(State root,Set<State> visitedStates){
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

    public ArrayList<State> printPath(Set<State> visitedStates, ArrayList<State> path) {
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




}
