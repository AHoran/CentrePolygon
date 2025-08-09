package centrepolygon;
import centrepolygon.PolyUtils;
import centrepolygon.CentrePolygon;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam.horan
 */
public class CentrePolygonTest {
    public CentrePolygonTest() {}
    @Test
    public void testFindCentreTriangle1() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(10,10));
        points.add(new Point(30,30));
        points.add(new Point(30,10));
        Polygon poly = centrepolygon.PolyUtils.CreatePolygonFromList(points);
        Point findCentre = centrepolygon.CentrePolygon.findCentre(poly);
        assertEquals("Incentre should be 24.1,15.9", 24.1, findCentre.getX(), 1);
        assertEquals("Incentre should be 24.1,15.9", 15.9, findCentre.getY(), 1);
    }
    @Test
    public void testFindCentreTriangle2() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(25,62));
        points.add(new Point(346,14));
        points.add(new Point(43,146));
        Polygon poly = centrepolygon.PolyUtils.CreatePolygonFromList(points);
        Point findCentre = centrepolygon.CentrePolygon.findCentre(poly);
        assertEquals(70.1002, findCentre.getX(), 1);
        assertEquals( 93.2293, findCentre.getY(), 1);
    }
    @Test
    public void testSimpleRectangle(){
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(-100,-100));
        points.add(new Point(200,-100));
        points.add(new Point(200,50));
        points.add(new Point(-100,50));
        Polygon poly = centrepolygon.PolyUtils.CreatePolygonFromList(points);
        Point findCentre = centrepolygon.CentrePolygon.findCentre(poly);
        assertEquals(-25.0, findCentre.getY(), 1);
        //we can't assert an X for this rectangle
        //assertEquals(50.0, findCentre.getX(), 1);
    }
}
