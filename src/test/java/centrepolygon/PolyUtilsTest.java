package centrepolygon;
import centrepolygon.PolyUtils;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class PolyUtilsTest {
    public PolyUtilsTest() {}
    @Test
    public void testCreatePolygonFromList() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(0,5));
        points.add(new Point(10,12));
        points.add(new Point(-1, 9));
    Polygon poly = centrepolygon.PolyUtils.CreatePolygonFromList(points);
        assertEquals("Number of vertexes", 3, poly.npoints);
        assertEquals("First Point x" , 0, poly.xpoints[0]);
        assertEquals("First Point y" , 5, poly.ypoints[0]);
        assertEquals("Last Point x" , -1, poly.xpoints[2]);
        assertEquals("Last Point y" , 9, poly.ypoints[2]);
    }
    @Test
    public void testGetMin() {
        int[] xpoints = new int[]{ 7 , 10 , 1, -6 , 100};
        int[] ypoints = new int[]{ 10, 20, 30, 5, 1};
        Polygon poly = new Polygon(xpoints,ypoints,xpoints.length);
    Point min = centrepolygon.PolyUtils.getMin(poly);
        assertEquals(-6, min.x);
        assertEquals(1, min.y);
    }
    @Test
    public void testGetMax() {
        int[] xpoints = new int[]{ 7 , 10 , 1, -6 , 100};
        int[] ypoints = new int[]{ 10, 20, 30, 5, 1};
        Polygon poly = new Polygon(xpoints,ypoints,xpoints.length);
    Point max = centrepolygon.PolyUtils.getMax(poly);
        assertEquals(100, max.x);
        assertEquals(30, max.y);
    }
}
