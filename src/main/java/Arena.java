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
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(width / 2, height / 2);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Hero getHero() {
        return this.hero;
    }

    public List<Wall> getWalls() {
        return this.walls;
    }

    public List<Coin> getCoins() {
        return this.coins;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        hero.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
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

    private List<Monster> createMonsters() {
        Random random = new Random();
        List<Monster> monsters = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            Position position = new Position(x, y);
            boolean flag = true;
            // guarantee the monster doesn't spawn on top of the hero
            if (hero.getPosition().equals(position)) {
                flag = false;
                i--;
            }
            // guarantee the monster doesn't spawn on top of a coin
            for (Coin coin : coins)
                if (coin.getPosition().equals(position)) {
                    flag = false;
                    i--;
                }
            // guarantee the monster doesn't spawn on top of another monster
            for (Monster monster : monsters)
                if (monster.getPosition().equals(position)) {
                    flag = false;
                    i--;
                }
            if (flag == true)
                monsters.add(new Monster(x, y));
        }

        return monsters;
    }

    private void moveHero(Position position) {
        retrieveCoins(position);
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
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

    private boolean verifyMonsterCollision(Position position) {
        for (Monster monster : monsters)
            if (monster.getPosition().equals(position)) {
                System.out.println("Game Over");
                return true;
            }
        return false;
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position position = monster.move();
            if (canMonsterMove(position))
                monster.setPosition(position);
        }
    }

    private boolean canMonsterMove(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
        for (Coin coin : coins)
            if (coin.getPosition().equals(position))
                return false;
        return true;
    }

    public boolean processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                moveMonsters();
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                moveMonsters();
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                moveMonsters();
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                moveMonsters();
                if (verifyMonsterCollision(hero.getPosition()))
                    return true;
                break;
            case EOF:
                return true;
            case Character:
                return key.getCharacter() == 'q';
            default:
                break;
        }
        return false;
    }
}
