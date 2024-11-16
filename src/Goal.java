import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.Objects;

@Data
@Builder
public class Goal {
    private char C;
    private int x;
    private int y;
    private Color color;

    public Goal(char c, int x, int y, Color color) {
        this.C = c;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return C == goal.C && x == goal.x && y == goal.y && Objects.equals(color, goal.color);
    }

    @Override
    public String toString() {

        String col="";
        if(color == Color.RED)
            col = "red";
        if(color == Color.GREEN)
            col = "green";
        if (color == Color.BLUE)
            col = "blue";
        if (color == Color.YELLOW)
            col = "yellow";
        if (color == Color.PINK)
            col = "pink";
        return "Goal{" +
                "C=" + C +
                ", x=" + x +
                ", y=" + y +
                ", color=" + col +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(C, x, y, color);
    }
}
