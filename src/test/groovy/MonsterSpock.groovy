import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification

class MonsterSpock extends Specification {
    private def monsterTest;

    def setup() {
        monsterTest = new Monster(6, 6)
    }

    def "movement"() {
        expect:
        monsterTest.move() == new Position(7, 6) || new Position(5, 6) || new Position(6, 7) || new Position(6, 5)
        monsterTest.move() == new Position(7, 6) || new Position(5, 6) || new Position(6, 7) || new Position(6, 5)
        monsterTest.move() == new Position(7, 6) || new Position(5, 6) || new Position(6, 7) || new Position(6, 5)
        monsterTest.move() == new Position(7, 6) || new Position(5, 6) || new Position(6, 7) || new Position(6, 5)
    }

    def "draw"() {
        given:
        def graphics = Mock(TextGraphics)

        when:
        monsterTest.draw(graphics)

        then:
        1 * graphics.setForegroundColor(TextColor.Factory.fromString("#922B21"))
        1 * graphics.putString(new TerminalPosition(monsterTest.getPosition().getX(), monsterTest.getPosition().getY()), "M")
    }
}
