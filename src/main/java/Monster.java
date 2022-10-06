import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) { super(x, y); }

    public Position move() {
        Random random = new Random();
        Position[] positions = new Position[]{ new Position(0, 1), new Position(0, -1), new Position(1, 0), new Position(-1, 0)};
        int r = random.nextInt(4);
        Position position = getPosition().add(positions[r]);
        return position;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#922B21"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
