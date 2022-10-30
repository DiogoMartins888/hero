import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification

class WallSpock extends Specification {
    private def testWall;

    def setup() {
        testWall = new Wall(3, 7)
    }

    def "draw"() {
        given:
        def graphics = Mock(TextGraphics)

        when:
        testWall.draw(graphics)

        then:
        1 * graphics.setForegroundColor(TextColor.Factory.fromString("#5B2C6F"))
        1 * graphics.putString(new TerminalPosition(testWall.getPosition().getX(), testWall.getPosition().getY()), "W")
    }
}
