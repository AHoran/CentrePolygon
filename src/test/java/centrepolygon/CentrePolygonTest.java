package centrepolygon;
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
    public void testSelfIntersectingPolygon() {
        // Bowtie shape (self-intersecting)
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 2));
        points.add(new Point(0, 2));
        points.add(new Point(2, 0));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        // Centre should be within the bounds
        assertTrue(centre.getX() >= 0 && centre.getX() <= 2);
        assertTrue(centre.getY() >= 0 && centre.getY() <= 2);
    }
    @Test
    public void testRandomOrderPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(5, 5));
        points.add(new Point(1, 1));
        points.add(new Point(3, 3));
        points.add(new Point(2, 2));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        // Centre should be within the bounds
        assertTrue(centre.getX() >= 1 && centre.getX() <= 5);
        assertTrue(centre.getY() >= 1 && centre.getY() <= 5);
    }
    @Test
        public void testLargePolygon() {
            ArrayList<Point> points = new ArrayList<>();
            int large = 10_000;
            points.add(new Point(large, large));
            points.add(new Point(large, -large));
            points.add(new Point(-large, -large));
            points.add(new Point(-large, large));
            Polygon poly = PolyUtils.CreatePolygonFromList(points);
            Point centre = CentrePolygon.findCentre(poly);
            // Centre should be near (0,0) and within the bounds
            assertTrue(centre.getX() <= large && centre.getX() >= -large);
            assertTrue(centre.getY() <= large && centre.getY() >= -large);
            assertEquals(0, centre.getX(), large * 0.1); // within 10% of centre
            assertEquals(0, centre.getY(), large * 0.1);
            // Additional robustness: check that the centre is inside the polygon
            assertTrue(poly.contains(centre));
        }

        @Test
        public void testExtremeValuePolygon() {
            int extreme = Integer.MAX_VALUE / 1000; // avoid overflow in calculations
            ArrayList<Point> extremePoints = new ArrayList<>();
            extremePoints.add(new Point(extreme, extreme));
            extremePoints.add(new Point(extreme, -extreme));
            extremePoints.add(new Point(-extreme, -extreme));
            extremePoints.add(new Point(-extreme, extreme));
            Polygon extremePoly = PolyUtils.CreatePolygonFromList(extremePoints);
            Point extremeCentre = null;
            try {
                extremeCentre = CentrePolygon.findCentre(extremePoly);
            } catch (Exception e) {
                fail("Algorithm should not throw for extreme values: " + e.getMessage());
            }
            assertNotNull(extremeCentre);
            assertTrue(extremePoly.contains(extremeCentre));
            assertEquals(0, extremeCentre.getX(), extreme * 0.1);
            assertEquals(0, extremeCentre.getY(), extreme * 0.1);
    }
    @Test
    public void testNullPolygon() {
        try {
            CentrePolygon.findCentre(null);
            fail("Should throw exception for null polygon");
        } catch (Exception e) {
            // Expected
        }
    }

    @Test
    public void testNullPointsInPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(null);
        points.add(new Point(1, 1));
        try {
            Polygon poly = PolyUtils.CreatePolygonFromList(points);
            fail("Should throw IllegalArgumentException for null point");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
    @Test
    public void testCollinearPointsPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        // Should be average of all points
        assertEquals(1, centre.getX(), 0.1);
        assertEquals(1, centre.getY(), 0.1);
    }

    @Test
    public void testDuplicatePointsPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 0));
        points.add(new Point(2, 2));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        // Should be average of all points
        assertEquals((0+0+2)/3, centre.getX(), 0.1);
        assertEquals((0+0+2)/3, centre.getY(), 0.1);
    }
    @Test
    public void testEmptyPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        assertThrows(IllegalArgumentException.class, () -> CentrePolygon.findCentre(poly));
    }

    @Test
    public void testSinglePointPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        assertEquals(1, centre.getX(), 1);
        assertEquals(1, centre.getY(), 1);
    }

    @Test
    public void testTwoPointPolygon() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(3, 3));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point centre = CentrePolygon.findCentre(poly);
        assertEquals(2, centre.getX(), 1);
        assertEquals(2, centre.getY(), 1);
    }
    @Test
    public void testDefaultConstructor() {
        CentrePolygon cp = new CentrePolygon();
        assertNotNull(cp);
    }
    @Test
    public void testFindCentreTriangle1() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(10,10));
        points.add(new Point(30,30));
        points.add(new Point(30,10));
    Polygon poly = PolyUtils.CreatePolygonFromList(points);
    Point findCentre = CentrePolygon.findCentre(poly);
        assertEquals("Incentre should be 24.1,15.9", 24.1, findCentre.getX(), 1);
        assertEquals("Incentre should be 24.1,15.9", 15.9, findCentre.getY(), 1);
    }
    @Test
    public void testFindCentreTriangle2() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(25,62));
        points.add(new Point(346,14));
        points.add(new Point(43,146));
    Polygon poly = PolyUtils.CreatePolygonFromList(points);
    Point findCentre = CentrePolygon.findCentre(poly);
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
    Polygon poly = PolyUtils.CreatePolygonFromList(points);
    Point findCentre = CentrePolygon.findCentre(poly);
        assertEquals(-25.0, findCentre.getY(), 1);
        //we can't assert an X for this rectangle
        //assertEquals(50.0, findCentre.getX(), 1);
    }
    @Test
    public void testFindCentreConcavePolygon() {
        // Concave polygon (arrow shape)
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(4, 0));
        points.add(new Point(4, 2));
        points.add(new Point(2, 1)); // Concave vertex
        points.add(new Point(0, 2));
        Polygon poly = PolyUtils.CreatePolygonFromList(points);
        Point findCentre = CentrePolygon.findCentre(poly);
        // Assert that the centre is within the bounds of the polygon
        assertTrue("Centre X should be between 0 and 4", findCentre.getX() >= 0 && findCentre.getX() <= 4);
        assertTrue("Centre Y should be between 0 and 2", findCentre.getY() >= 0 && findCentre.getY() <= 2);
    }
}
