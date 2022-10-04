import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall {
    private Position position;

    public Wall(int x, int y) { position = new Position(x, y); }

    public Wall(Position position) { this.position = position; }

    public Position getPosition() { return position; }

    public void setPosition() { this.position = position; }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#5B2C6F"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "W");
    }
}
