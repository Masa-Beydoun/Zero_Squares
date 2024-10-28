import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
public class Goal {
    private int x;
    private int y;
    private Color color;
    Goal(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
