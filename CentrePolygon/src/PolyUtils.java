
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

/**
 *
 * @author adam.horan
 */
public class PolyUtils {
    
    public static Polygon CreatePolygonFromList(List<Point> points){
        Polygon poly = new Polygon();
        for(Point p : points){
            poly.addPoint((int)p.getX(), (int)p.getY());
        }
        
        return poly;
    }
    
    public static Point getMin(Polygon poly){
        Point p = new Point(poly.xpoints[0], poly.ypoints[0]);
        for (int i = 0; i < poly.npoints; i++) {
            if(poly.xpoints[i] < p.x){
                p.x = poly.xpoints[i];
            }
            if(poly.ypoints[i] < p.y){
                p.y = poly.ypoints[i];
            }
        }
        return p;
    }
    
     public static Point getMax(Polygon poly){
        Point p = new Point(poly.xpoints[0], poly.ypoints[0]);
        for (int i = 0; i < poly.npoints; i++) {
            if(poly.xpoints[i] > p.x){
                p.x = poly.xpoints[i];
            }
            if(poly.ypoints[i] > p.y){
                p.y = poly.ypoints[i];
            }
        }
        return p;
    }
    
}
