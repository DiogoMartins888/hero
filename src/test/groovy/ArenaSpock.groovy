import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import org.w3c.dom.Text
import spock.lang.Specification

class ArenaSpock extends Specification {
    private def testArena

    def setup() {
        testArena = new Arena(8, 4)
    }

    def "createWalls"() {
        when:
        def result = testArena.createWalls()

        then:
        def walls = new ArrayList<Wall>()
        walls.add(new Wall(0, 0)); walls.add(new Wall(0, 3))
        walls.add(new Wall(1, 0)); walls.add(new Wall(1, 3))
        walls.add(new Wall(2, 0)); walls.add(new Wall(2, 3))
        walls.add(new Wall(3, 0)); walls.add(new Wall(3, 3))
        walls.add(new Wall(4, 0)); walls.add(new Wall(4, 3))
        walls.add(new Wall(5, 0)); walls.add(new Wall(5, 3))
        walls.add(new Wall(6, 0)); walls.add(new Wall(6, 3))
        walls.add(new Wall(7, 0)); walls.add(new Wall(7, 3))
        walls.add(new Wall(0, 1)); walls.add(new Wall(7, 1))
        walls.add(new Wall(0, 2)); walls.add(new Wall(7, 2))

        int i = 0
        for (Wall wall : walls) {
            wall == result.get(i)
            i++
        }
    }

    def "createCoins"() {
        expect: // since coin spawns are random, it is only possible to check whether the right amount of coins was created
        testArena.createCoins().size() == 5
    }

    def "createMonsters"() {
        expect: // since monster spawns are random, it is only possible to check whether the right amount of monsters was created
        testArena.createMonsters().size() == 3
    }

    def "canHeroMove"() {
        expect:
        testArena.canHeroMove(new Position(0, 0)) == false
        testArena.canHeroMove(new Position( testArena.width - 1, testArena.height - 1)) == false
        testArena.canHeroMove(new Position(0, testArena.height - 1)) == false
        testArena.canHeroMove(new Position(testArena.width - 1, 0)) == false
        testArena.canHeroMove(new Position(0, 2)) == false
        testArena.canHeroMove(new Position(2, 2)) == true
    }

    def  "draw"() {
        given:
        def graphics = Mock(TextGraphics)

        when:
        testArena.draw(graphics)

        then:
        1 * graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"))
        1 * graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(testArena.getWidth(), testArena.getHeight()), ' ')

        /* can't test if these methods were called because the objects are not mocks
        for (Wall wall : testArena.getWalls()) {
            1 * wall.draw(graphics)
        }
        1 * testArena.getHero().draw(graphics)
        for (Coin coin : testArena.getCoins()) {
            1 * coin.draw(graphics)
        }
        for (Monster monster : testArena.getMonsters()) {
            1 * monster.draw(graphics)
        }
        */
    }
}
