import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification

class CoinSpock extends Specification {
    private def coinTest;

    def setup() {
        coinTest = new Coin(8, 8)
    }

    def "draw"() {
        given:
        def graphics = Mock(TextGraphics)

        when:
        coinTest.draw(graphics)

        then:
        1 * graphics.setForegroundColor(TextColor.Factory.fromString("#F1C40F"))
        1 * graphics.putString(new TerminalPosition(coinTest.getPosition().getX(), coinTest.getPosition().getY()), "C")
    }
}
