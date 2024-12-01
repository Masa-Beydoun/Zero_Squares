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
        ArrayList<State> visitedStates = new ArrayList<>();
        Stack<State> stack = new Stack<>();
        root.parent = null;
        stack.add(root);
        while(!stack.isEmpty()){
            System.out.println("number of visits: " + visitedStates.size());
            State currentState = stack.pop();
            boolean flag = false;
            for(State visitedState : visitedStates){
                if(visitedState.equals(currentState)) {
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("DFS");
                return getPath(currentState,visitedStates);
            }
            ArrayList<State>nextStates =  currentState.possibleBoards();
            for(State nextState : nextStates){
                stack.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }

    public void initiate_DfS(State root){
        ArrayList<State> visitedStates = new ArrayList<>();
        DFS_Recursive(root,visitedStates);
    }

    ArrayList<State> globalPath = new ArrayList<>();
    public boolean DFS_Recursive(State root,ArrayList<State> visitedStates){
        if(checkGoal(root)) {
            System.out.println("DFS Recursive");
            globalPath = getPath(root,visitedStates);
            return true;
        }
        boolean flag = false;
        for(State visitedState : visitedStates){
            if(visitedState.equals(root)) {
                flag = true;
                break;
            }
        }
        if(flag) return false;
        visitedStates.add(root);
        root.possibleBoards();
        ArrayList<State> nextStates =  root.possibleBoards();
        for(State nextState : nextStates){
            nextState.parent = root;
            flag = DFS_Recursive(nextState,visitedStates);
            if(flag) return true;

        }
        return false;

    }


    public ArrayList<State> UCS(State root) {
        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s->s.cost));
        root.parent = null;
        root.cost = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
//            System.out.println("number of visited states: " + visitedStates.size() +"  "+ currentState.cost);
//            System.out.println(currentState);
            boolean flag = false;
            for(State visitedState : visitedStates){
                if(visitedState.equals(currentState)) {
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("UCS");
                return getPath(currentState,visitedStates);
            }
            currentState.possibleBoards();
            ArrayList<State> nextStates =  currentState.possibleBoards();
            for (State nextState : nextStates) {
                nextState.cost = currentState.cost + 1;
                if (visitedStates.contains(nextState)) continue;
                nextState.parent = currentState;
                queue.add(nextState);
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
            boolean flag = false;
            for(State visitedState : visitedStates){
                if(visitedState.equals(currentState)) {
                    flag = true;
                    break;
                }
            }
            System.out.println("state is not visited");
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("BFS");
                return getPath(currentState,visitedStates);
            }
            currentState.possibleBoards();
            ArrayList<State> nextStates =  currentState.possibleBoards();
            for(State nextState : nextStates){
                queue.add(nextState);
                nextState.parent = currentState;
            }
        }
        return null;
    }

    public ArrayList<State> StepsHillClimbing(State root) {
        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::heuristic));
        root.parent = null;
        root.cost = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            System.out.println("queus size"+queue.size());
//            System.out.println("number of visited "+visitedStates.size());
            State currentState = queue.remove();
            System.out.println("curent heu"+ currentState.heuristic());
//            System.out.println(currentState);
            boolean flag = false;
            for(State visitedState : visitedStates){
                if(visitedState.equals(currentState)) {
                    flag = true;
                    break;
                }
            }
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("UCS");
                return getPath(currentState,visitedStates);
            }
            boolean foundLess = false;
            ArrayList<State> nextStates =  currentState.possibleBoards();

            for (State nextState : nextStates) {
                flag=false;
                for(State visitedState : visitedStates){
                    if(visitedState.equals(nextState)) {
                        flag= true;
                        break;
                    }
                }
                if(flag) continue;
                System.out.println("next heu"+ nextState.heuristic());
                if(nextState.heuristic()<=currentState.heuristic()) {
                    foundLess=true;
                }
                else continue;

                nextState.parent = currentState;
                queue.add(nextState);
            }
            if(!foundLess) return null;
        }
        return null;
    }


    public ArrayList<State> simpleHillClimbing(State root){
        ArrayList<State> visitedStates = new ArrayList<>();
        Queue<State> queue = new ArrayDeque<>();
        root.parent = null;
        root.cost = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            System.out.println("number of visits: " + visitedStates.size());
            State currentState = queue.poll();

            boolean flag = false;
            for(State visitedState : visitedStates){
                if(visitedState.equals(currentState)) {
                    flag= true;
                    break;
                }
            }
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("simple");
                return getPath(currentState, visitedStates);
            }
            int minCost = currentState.cost+currentState.heuristic();

            ArrayList<State> nextStates = currentState.possibleBoards();
            boolean f=false;
            for (State nextState : nextStates) {
                for(State visitedState : visitedStates){
                    if(visitedState.equals(nextState)) {
                        flag= true;
                        break;
                    }
                }
                if(flag) continue;
                if(nextState.heuristic()<=minCost) {
                    queue.add(nextState);
                    f=true;
                    continue;
                }
                if(!f) return null;
                nextState.cost = currentState.cost + 1;
                nextState.parent = currentState;
            }

        }
        return null;
    }


    public ArrayList<State> AStarSearch(State root) {
//        System.out.println("A star starting");
        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic));

        root.parent = null;
        root.cost = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
//            System.out.println("number of visits: " + visitedStates.size());
//            System.out.println("number of queue: " + queue.size());
            State currentState = queue.remove();
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("A* Search");
                return getPath(currentState, visitedStates);
            }
//            System.out.println("current state " + currentState);
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                boolean flag = false;
                System.out.println("checking next state");
                for(State visitedState : visitedStates){
                    if(visitedState.equals(nextState)) {
                        flag= true;
                        break;
                    }
                }
//                System.out.println(flag);
                if(flag) continue;
//                System.out.println(nextState);
                nextState.heuristic=nextState.heuristic();
                nextState.cost = currentState.cost + 1;
                nextState.parent = currentState;
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
