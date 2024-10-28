import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
public class Goal {
    private int x;
    private int y;
    private Color color;
}
