import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Objects;

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
}
