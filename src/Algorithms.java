import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Collections.reverse;

public class Algorithms {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLACK = "\u001B[40m";



    public ArrayList<State> DFS(State root){
        ArrayList<State> visitedStates = new ArrayList<>();
        Stack<State> stack = new Stack<>();
        root.parent = null;
        stack.add(root);

        while(!stack.isEmpty()){
            State currentState = stack.pop();
            if(currentState == null) continue;
//            System.out.println(currentState);
            visitedStates.add(currentState);
            currentState.possibleBoards();
            for(State nextState : currentState.nextStates){
                if(nextState == null) continue;
                boolean found = false;
                for(State visited : visitedStates) {
                    if (visited.equals(nextState)) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    stack.add(nextState);
                    nextState.parent = currentState;
                    if (checkGoal(nextState)) {
                        ArrayList<State> path = new ArrayList<>();
                        path.add(currentState);
                        while (currentState.parent != null) {
                            path.add(currentState.parent);
                            currentState = currentState.parent;
                        }
                        reverse(path);
                        System.out.println("DFS");
                        return printPath(visitedStates, path);
                    }
                }

            }
        }
        return null;
    }



    public ArrayList<State> BFS(State root){
        ArrayList<State> visitedStates = new ArrayList<>();
        Queue<State> queue = new ArrayDeque<>();
        root.parent = null;
        queue.add(root);
        while(!queue.isEmpty()){
            System.out.println("number of visits: " + visitedStates.size());
            State currentState = queue.remove();
            visitedStates.add(currentState);
            currentState.possibleBoards();
            for(State nextState : currentState.nextStates){
                if(nextState == null) continue;
                boolean found = false;
                for(State visited : visitedStates) {
                    if (visited.equals(nextState)) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    queue.add(nextState);
                    nextState.parent = currentState;
                    if (checkGoal(nextState)) {
                        ArrayList<State> path = new ArrayList<>();
                        path.add(currentState);
                        while (currentState.parent != null) {
                            path.add(currentState.parent);
                            currentState = currentState.parent;
                        }
                        reverse(path);
                        System.out.println("BFS");
                        return printPath(visitedStates, path);
                    }
                }

            }
        }
        return null;
    }

    public boolean checkGoal(State root){
        for(Stone stone : root.stones){
            if(!stone.isInGoal())
                return false;
        }
        return true;
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




}
