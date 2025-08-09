package centrepolygon;
import centrepolygon.PolyUtils;
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

	/**
	 * Find the centre of the largest circle that will fit in the specified polygon
	 * @param targetPolygon
	 * @return 
	 */
	public static Point findCentre(Polygon targetPolygon) {
		return findCentre(targetPolygon, 2.0, 40);
	}

	/**
	 * Find the centre of the largest circle that will fit in the specified polygon
	 * @param targetPolygon
	 * @param minimumAccuracy Keep iterating until the bounding box is smaller than this specified accuracy
	 * @param maxMisses How many loops to perform that don't discover a better candidate before resizing the bounding box.
	 * @return 
	 */
	public static Point findCentre(Polygon targetPolygon, double minimumAccuracy, int maxMisses) {
		Point PIA = new Point();
		double currentAccuracy = Double.MAX_VALUE;
		double minDist = Double.MAX_VALUE;
		double maxMinDist = 0;
		Point minBound = PolyUtils.getMin(targetPolygon);
		Point maxBound = PolyUtils.getMax(targetPolygon);
		double currentx, currenty;
		Point currentNode;
		int misses = 0;
		System.out.println(String.format("Initial bounds min: %1$s, %2$s max: %3$s, %4$s", minBound.getX(),minBound.getY(),maxBound.getX(),maxBound.getY()));
		new Random().setSeed(new Date().getTime());
		while (currentAccuracy > minimumAccuracy) {
			misses = 0;
			while (misses < maxMisses) {
				System.out.println(String.format("Misses: %1$s, miss Limit: %2$s", misses, maxMisses));
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
		return PIA;
	}
}
