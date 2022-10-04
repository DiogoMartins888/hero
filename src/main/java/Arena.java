import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero = new Hero(5, 5);
    private List<Wall> walls;
    private List<Coin> coins;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        hero.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        List<Coin> coins = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            Position position = new Position(x, y);
            boolean flag = true;
            // guarantee the coin doesn't spawn on top of the hero
            if (hero.getPosition().equals(position)) {
                flag = false;
                i--;
            }
            // guarantee the coin doesn't spawn on top of another coin
            for (Coin coin : coins)
                if (coin.getPosition().equals(position)) {
                    flag = false;
                    i--;
                }
            if (flag == true)
                coins.add(new Coin(x, y));
        }

        return coins;
    }

    private void moveHero(Position position) {
        retrieveCoins(position);
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    private void retrieveCoins(Position position) {
        Iterator itr = coins.iterator();
        while (itr.hasNext()) {
            Coin coin = (Coin) itr.next();
            if (coin.getPosition().equals(position)) {
                itr.remove();
                break;
            }
        }
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            default:
                break;
        }
    }
}
