import java.util.ArrayList;

public class FullGame {


    State state = new State();

    ArrayList <State> visitedStates = new ArrayList<>();

    public State move(String dir, boolean flag){
//        System.out.println("full game");
        State myState = state.move(dir);
        state.possibleBoards();
        state = myState;

        boolean found = false;
        for(State visited : visitedStates){
            if(visited.equals(state)){
                System.out.println("this state has already been visited");
                found = true;
            }
        }
        if(!found){
            visitedStates.add(state);
        }
//        System.out.println("visited states "+ visitedStates.toString());
        return state;
    }
}
