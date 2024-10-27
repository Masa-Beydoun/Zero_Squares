import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stone {
    private Color color;
    private int x;
    private int y;
    private boolean inGoal;
}
