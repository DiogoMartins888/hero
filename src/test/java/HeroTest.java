import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class HeroTest {

    @Test
    public void instantiation() {
        Hero hero = new Hero(0, 0);
        Position expected = new Position(0, 0);
        Assertions.assertEquals(hero.getPosition(), expected);

        hero = new Hero(-5, -5);
        expected = new Position(-5, -5);
        Assertions.assertEquals(hero.getPosition(), expected);
    }

    @Test
    public void movement() {
        Hero hero = new Hero(10, 10);
        Position expected = new Position(10, 10);

        expected.setY(9);
        Assertions.assertEquals(hero.moveUp(), expected);
        expected.setY(10);

        expected.setX(9);
        Assertions.assertEquals(hero.moveLeft(), expected);
        expected.setX(10);

        expected.setY(11);
        Assertions.assertEquals(hero.moveDown(), expected);
        expected.setY(10);

        expected.setX(11);
        Assertions.assertEquals(hero.moveRight(), expected);
        expected.setY(10);
    }

    @Test
    public void draw() {
        Hero hero = new Hero(10, 10);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        hero.draw(graphics);
        verify(graphics, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        verify(graphics, Mockito.times(1)).enableModifiers(SGR.BOLD);
        verify(graphics, Mockito.times(1)).putString(new TerminalPosition(hero.getPosition().getX(), hero.getPosition().getY()), "X");
    }

}
