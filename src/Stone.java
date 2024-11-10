import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Objects;

import static java.awt.Color.RED;
import static java.awt.Color.red;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stone {
    private char c;
    private Color color;
    private int x;
    private int y;
    private boolean inGoal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stone stone = (Stone) o;
        return c == stone.c && x == stone.x && y == stone.y && inGoal == stone.inGoal && Objects.equals(color, stone.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, color, x, y, inGoal);
    }


    @Override
    public String toString() {
        if(color == RED) {
            return Algorithms.RED + "Stone{" +
                    "c=" + c +
                    ", color=red"+
                    ", x=" + x +
                    ", y=" + y +
                    ", inGoal=" + inGoal +
                    '}' + Algorithms.RESET;
        }
        if(color == Color.BLUE) {
            return Algorithms.BLUE + "Stone{" +
                    "c=" + c +
                    ", color=blue" +
                    ", x=" + x +
                    ", y=" + y +
                    ", inGoal=" + inGoal +
                    '}' + Algorithms.RESET;
        }
        if(color == Color.GREEN) {
            return Algorithms.GREEN + "Stone{" +
                    "c=" + c +
                    ", color=green" +
                    ", x=" + x +
                    ", y=" + y +
                    ", inGoal=" + inGoal +
                    '}' + Algorithms.RESET;
        }
        if(color == Color.YELLOW) {
            return Algorithms.YELLOW + "Stone{" +
                    "c=" + c +
                    ", color=yellow" +
                    ", x=" + x +
                    ", y=" + y +
                    ", inGoal=" + inGoal +
                    '}' + Algorithms.RESET;
        }
         return Algorithms.PURPLE + "Stone{" +
                "c=" + c +
                ", color=pink" +
                ", x=" + x +
                ", y=" + y +
                ", inGoal=" + inGoal +
                '}' + Algorithms.RESET;
    }
}
