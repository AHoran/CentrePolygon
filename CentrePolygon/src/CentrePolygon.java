
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.Random;

/**
 *
 * @author adam.horan
 */
public class CentrePolygon {

    public static Point findCentre(Polygon targetPolygon) {
        Point PIA = new Point();
        double currentAccuracy = Double.MAX_VALUE;
        double minimumAccuracy = 0.0;
        double minDist = Double.MAX_VALUE;
        double maxMinDist = 0;
        Point minBound = PolyUtils.getMin(targetPolygon);
        Point maxBound = PolyUtils.getMax(targetPolygon);
        int currentx, currenty;
        Point currentNode;
        int misses = 0;
        int missLimit = 10;
        while (currentAccuracy > minimumAccuracy) {
            while (misses < missLimit) {
                currentx = new Random().nextInt(maxBound.x - minBound.x) + minBound.x;
                currenty = new Random().nextInt(maxBound.y - minBound.y) + minBound.y;
                currentNode = new Point(currentx, currenty);
                if (targetPolygon.contains(currentNode)) {
                    for (int i = 0; i < targetPolygon.npoints; i++) {
                        double dist;
                        if(i == targetPolygon.npoints - 1){
                            dist = Line2D.ptLineDist((double)targetPolygon.xpoints[i], (double)targetPolygon.ypoints[i], (double)targetPolygon.xpoints[0], (double)targetPolygon.ypoints[0], (double)currentx, (double)currenty);
                        }else{
                            dist = Line2D.ptLineDist((double)targetPolygon.xpoints[i], (double)targetPolygon.ypoints[i], (double)targetPolygon.xpoints[i+1], (double)targetPolygon.ypoints[i+1], (double)currentx, (double)currenty);
                        }
                        if(dist < minDist){
                            minDist = dist;
                            PIA = currentNode;
                        }
                    }
                    
                    if(minDist > maxMinDist){
                        maxMinDist = minDist;
                        misses = 0;
                    }else{
                        misses ++;
                    }
                }
            }
            currentAccuracy = Math.min(maxBound.x - minBound.x, maxBound.y - minBound.x);
            //    # update the bounds of the region
//    min_x := PIA(x) - (max_x - min_x) / (sqrt(2) * 2)
//    max_x := PIA(x) + (max_x - min_x) / (sqrt(2) * 2)
//    min_y := PIA(y) - (max_y - min_y) / (sqrt(2) * 2)
//    max_y := PIA(y) + (max_y - min_y) / (sqrt(2) * 2)
            double factor = Math.sqrt(2.0) * 2.0;
            int minBound_x = (int)(PIA.x - (maxBound.x - minBound.x) / factor);
            int maxBound_x = (int)(PIA.x + (maxBound.x - minBound.x) / factor);
            int minBound_y = (int)(PIA.y - (maxBound.y - minBound.y) / factor);
            int maxBound_y = (int)(PIA.y + (maxBound.y - minBound.y) / factor);
            
            minBound = new Point(minBound_x, minBound_y);
            maxBound = new Point(maxBound_x, maxBound_y);
                    
        }

//    while accuracy > minimum_accuracy do:
//    # begin loop through nodes
//    while consecutive_misses < k do:
//    # select coordinates at random within bounds
//    x := random(min_x, max_x)
//    y := random(min_y, max_y)
//    node := (x, y)
//    # loop through edges and find shortest distance
//    for edge := first_edge to last_edge do:
//    if distance(node, edge) < smallest_distance
//    smallest_distance := distance(node, edge)
//    PIA := node
//    end if
//    end do
//    # maximize the minimum distance through iterations
//    # and keep track of the consecutive number of times
//    # that a smallest distance has not been found
//    if smallest_distance > maximin_distance
//    maximin_distance := smallest_distance
//    consecutive_misses := 0
//    else
//    consecutive_misses := consecutive_misses + 1
//    end if
//    end do
//    # calculate current level of accuracy based on the
//    # smallest distance between the upper and lower bound
//    accuracy := min(max_x - min_x, max_y - min_y)
//    # update the bounds of the region
//    min_x := PIA(x) - (max_x - min_x) / (sqrt(2) * 2)
//    max_x := PIA(x) + (max_x - min_x) / (sqrt(2) * 2)
//    min_y := PIA(y) - (max_y - min_y) / (sqrt(2) * 2)
//    max_y := PIA(y) + (max_y - min_y) / (sqrt(2) * 2)
//    end do
//    return PIA
        return PIA;
    }
}
