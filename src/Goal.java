import lombok.Builder;
import lombok.Data;

import java.awt.*;

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

}
