import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification

class HeroSpock extends Specification {
    private def testHero

    def setup() {
        testHero = new Hero(5, 5)
    }

    def "movement"() {
        expect:
        testHero.moveUp() == new Position(5, 4)
        testHero.moveDown() == new Position(5, 6)
        testHero.moveLeft() == new Position(4, 5)
        testHero.moveRight() == new Position(6, 5)
    }

    def "draw"() {
        given:
        def graphics = Mock(TextGraphics)

        when:
        testHero.draw(graphics)

        then:
        1 * graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"))
        1 * graphics.enableModifiers(SGR.BOLD);
        1 * graphics.putString(new TerminalPosition(testHero.getPosition().getX(), testHero.getPosition().getY()), "X")
    }
}
