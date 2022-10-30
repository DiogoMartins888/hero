import spock.lang.Specification

class PositionSpock extends Specification {
    private def testPosition;

    def setup() {
        testPosition = new Position(12, 12)
    }

    def "add"() {
        given:
        def otherPosition = new Position(3, 3)

        expect:
        testPosition.add(otherPosition) == new Position(15, 15)
    }
}
