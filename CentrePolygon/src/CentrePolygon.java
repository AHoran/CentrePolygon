
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author adam.horan
 */
public class CentrePolygon {

    public static Point findCentre(Polygon targetPolygon) {
        Point PIA = new Point();
        double currentAccuracy = Double.MAX_VALUE;
        double minimumAccuracy = 2.0;
        double minDist = Double.MAX_VALUE;
        double maxMinDist = 0;
        Point minBound = PolyUtils.getMin(targetPolygon);
        Point maxBound = PolyUtils.getMax(targetPolygon);
        double currentx, currenty;
        Point currentNode;
        int misses = 0;
        int missLimit = 40;
        System.out.println(String.format("Initial bounds min: %1$s, %2$s max: %3$s, %4$s", minBound.getX(),minBound.getY(),maxBound.getX(),maxBound.getY()));
        new Random().setSeed(new Date().getTime());
        while (currentAccuracy > minimumAccuracy) {
            misses = 0;
            while (misses < missLimit) {
                System.out.println(String.format("Misses: %1$s, miss Limit: %2$s", misses, missLimit));

                currentx = (new Random().nextDouble() * (maxBound.x - minBound.x)) + minBound.x;
                currenty = new Random().nextInt(maxBound.y - minBound.y) + minBound.y;
                currentNode = new Point();
                currentNode.setLocation(currentx, currenty);
                System.out.println(String.format("New Test point: %1$s, %2$s", currentx, currenty));
                if (targetPolygon.contains(currentNode)) {
                    minDist = Double.MAX_VALUE;
                    for (int i = 0; i < targetPolygon.npoints; i++) {
                        double dist;
                        if (i == targetPolygon.npoints - 1) {
                            dist = Line2D.ptLineDist((double) targetPolygon.xpoints[i], (double) targetPolygon.ypoints[i], (double) targetPolygon.xpoints[0], (double) targetPolygon.ypoints[0], currentx, currenty);
                        } else {
                            dist = Line2D.ptLineDist((double) targetPolygon.xpoints[i], (double) targetPolygon.ypoints[i], (double) targetPolygon.xpoints[i + 1], (double) targetPolygon.ypoints[i + 1], currentx, currenty);
                        }
                        
                        if (dist < minDist) {
                            minDist = dist;
                        }
                        System.out.println(String.format("Distance to line: %1$s. Current min dist: %2$s", dist, minDist));

                    }
                    if (minDist > maxMinDist) {
                        maxMinDist = minDist;
                        PIA = currentNode;
                        System.out.println(String.format("New PIA found %1$s, %2$s. Max min dist %3$s", PIA.getX(), PIA.getY(),maxMinDist));
                        misses = 0;
                    } else {
                        misses++;
                    }


                } else {
                    System.out.println("Test point not in poly");
                }
            }
            currentAccuracy = Math.min(maxBound.x - minBound.x, maxBound.y - minBound.x);
            System.out.println(String.format("Current Accuracy %1$s", currentAccuracy));
            double factor = Math.sqrt(2.0) * 2.0;
            double minBound_x =  (PIA.x - ((maxBound.x - minBound.x) / factor));
            double maxBound_x =  (PIA.x + ((maxBound.x - minBound.x) / factor));
            double minBound_y =  (PIA.y - ((maxBound.y - minBound.y) / factor));
            double maxBound_y =  (PIA.y + ((maxBound.y - minBound.y) / factor));

            minBound.setLocation(minBound_x, minBound_y );
            maxBound.setLocation(maxBound_x, maxBound_y);
            System.out.println(String.format("New Bounds min: %1$s, %2$s max: %3$s, %4$s", minBound_x, minBound_y, maxBound_x, maxBound_y));
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
