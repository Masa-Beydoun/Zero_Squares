import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


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



    public ArrayList<State> DFS(State root,int index){
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


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
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"DFS",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);
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

    public void initiate_DfS(State root,int index){
        ArrayList<State> visitedStates = new ArrayList<>();
        DFS_Recursive(root,visitedStates,index);
    }

    ArrayList<State> globalPath = new ArrayList<>();
    public boolean DFS_Recursive(State root,ArrayList<State> visitedStates,int index){
        if(checkGoal(root)) {
            System.out.println("DFS Recursive");
            globalPath = getPath(root,visitedStates);
            writeLogToFile(index,"DFS_RECURSIVE",visitedStates,getPath(root,visitedStates),0,0);
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
            flag = DFS_Recursive(nextState,visitedStates,index);
            if(flag) return true;

        }
        return false;

    }


    public ArrayList<State> UCS(State root,int index) {
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s->s.calculateCost()));
        root.parent = null;
        root.cost = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
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
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"UCS",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);
                return getPath(currentState,visitedStates);
            }
            currentState.possibleBoards();
            ArrayList<State> nextStates =  currentState.possibleBoards();
            for (State nextState : nextStates) {
                nextState.cost = currentState.cost+nextState.calculateCost();
                if (visitedStates.contains(nextState)) continue;
                nextState.parent = currentState;
                queue.add(nextState);
            }
        }
        return null;
    }

    public ArrayList<State> BFS(State root,int index){
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


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
            if(flag) continue;
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("BFS");
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"BFS",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);

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

    public ArrayList<State> steepestHillClimbing(State root,int index) {
        System.out.println("steps hill climbing");
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic()));

        root.parent = null;
        root.cost = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            System.out.println();
            System.out.println("queue size: " + queue.size());
            State currentState = queue.remove();
            System.out.println("current hue"+currentState.heuristic());
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("step Search");
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"SteepestHillClimbing",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                System.out.println("checking next state ") ;
                if (existInVisited(nextState, visitedStates)) continue;
                System.out.println("true next hue "+nextState.heuristic());
                if(nextState.heuristic()>currentState.heuristic()) continue;
                queue.add(nextState);
                nextState.parent = currentState;
                System.out.println("next state added");
            }
        }
        return null;
    }

    public ArrayList<State> simpleHillClimbing(State root,int index){
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


        System.out.println("simple starting");
        ArrayList<State> visitedStates = new ArrayList<>();
        Queue<State> queue = new ArrayDeque<>();

        root.parent = null;
        root.cost = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            System.out.println("current state: " + currentState.heuristic());
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("simple hill climbing Search");
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"SimpleHillClimbing",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            boolean foundLess=false;
            for (State nextState : nextStates) {
                System.out.println("checking next state ");
                if (existInVisited(nextState, visitedStates)) continue;
                System.out.println(true + " next hue "+nextState.heuristic());
                if(nextState.heuristic()>currentState.heuristic()) continue;
                foundLess=true;
                nextState.parent = currentState;
                queue.add(nextState);
                System.out.println("next state added");
            }
            if(!foundLess) return null;
        }
        return null;
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

    public ArrayList<State> AStarSearch(State root,int index) {
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();


        ArrayList<State> visitedStates = new ArrayList<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost+s.heuristic()));

        root.parent = null;
        queue.add(root);

        while (!queue.isEmpty()) {
            System.out.println("visited states " + visitedStates.size());
            State currentState = queue.remove();
            visitedStates.add(currentState);
            if (checkGoal(currentState)) {
                System.out.println("A* Search");
                long endTime = System.nanoTime();
                long durationInNanoseconds = endTime - startTime;
                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoseconds);

                long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
                long memoryUsed = memoryUsedAfter - memoryUsedBefore;


                writeLogToFile(index,"aStarSearch",visitedStates,getPath(root,visitedStates),durationInMillis,memoryUsed);
                return getPath(currentState, visitedStates);
            }
            ArrayList<State> nextStates = currentState.possibleBoards();
            for (State nextState : nextStates) {
                if (existInVisited(nextState, visitedStates)) continue;
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

    public void writeLogToFile(int level,
                               String algo,
                               ArrayList<State> visitedStates,
                               ArrayList<State> path,
                               long time,
                               long memory) {

        String textToAdd = "level: " + level + "\n" +
                "algorithm: " + algo + "\n" +
                "number of visited states: " + visitedStates.size() + "\n" +
                "number of states in the path: " + path.size() + "\n" +
                "time: " + time + " ms\n" +
                "memory: " + memory + " bytes\n\n";

        String fileName = "src/outputs/"+algo + ".txt";

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


}
