import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) { super(x, y); }

    public Position move() {
        Random random = new Random();
        int x = random.nextInt(2);
        int y = random.nextInt(2);
        if (x == 0)
            x = -1;
        if (y == 0)
            y = -1;
        Position position = new Position(getPosition().getX() + x, getPosition().getY() + y);

        return position;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#922B21"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
