package game;

/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
USAGE: You are intended to instantiate this class with a set of points that
       forever defines its shape, and then modify it by repositioning and
       rotating that shape. In defining the shape, the relative positions
       of the points you provide are used, in other words: {(0,1),(1,1),(1,0)}
       is the same shape as {(9,10),(10,10),(10,9)}.
NOTE: You don't need to worry about the "magic math" details.

*/

class Polygon {
  private Point[] shape;   // An array of points.
  public Point position;   // The offset mentioned above.
  public double rotation; // Zero degrees is due east.
  
  public Polygon(Point[] inShape, Point inPosition, double inRotation) {
//    shape = inShape;
//    position = inPosition;
//    rotation = inRotation;
//    
//    // First, we find the shape's top-most left-most boundary, its origin.
//    Point origin = shape[0].clone();
//    for (Point p : shape) {
//      if (p.x < origin.x) origin.x = p.x;
//      if (p.y < origin.y) origin.y = p.y;
//    }
//    
//    // Then, we orient all of its points relative to the real origin.
//    for (Point p : shape) {
//      p.x -= origin.x;
//      p.y -= origin.y;
//    }
	  shape = new Point[inShape.length];
	    for (int i = 0; i < inShape.length; i++) {
	        shape[i] = inShape[i].clone(); // copy to avoid modifying original points
	    }

	    rotation = inRotation;

	    // Step 1: compute centroid of the raw shape
	    Point center = computeCentroid(shape);

	    // Step 2: shift all points so centroid is at (0,0)
	    for (Point p : shape) {
	        p.x -= center.x;
	        p.y -= center.y;
	    }

	    // Step 3: store position as the center of the polygon
	    position = inPosition;
  }
  
  // "getPoints" applies the rotation and offset to the shape of the polygon.
  public Point[] getPoints() {
//    Point center = findCenter();
//    Point[] points = new Point[shape.length];
//    for (int i = 0; i < shape.length; i++) {
////    for (Point p : shape) {
//      Point p = shape[i];
//      double x = ((p.x-center.x) * Math.cos(Math.toRadians(rotation)))
//               - ((p.y-center.y) * Math.sin(Math.toRadians(rotation)))
//               + center.x + position.x;
//      double y = ((p.x-center.x) * Math.sin(Math.toRadians(rotation)))
//               + ((p.y-center.y) * Math.cos(Math.toRadians(rotation)))
//               + center.y + position.y;
//      points[i] = new Point(x,y);
//    }
//    return points;
	  // "getPoints" applies the rotation and offset to the shape of the polygon.
	  
	  Point[] points = new Point[shape.length];
	    double radians = Math.toRadians(rotation);

	    for (int i = 0; i < shape.length; i++) {
	        Point p = shape[i];

	        // rotate around the tank's position (center)
	        double x = position.x + p.x * Math.cos(radians) - p.y * Math.sin(radians);
	        double y = position.y + p.x * Math.sin(radians) + p.y * Math.cos(radians);

	        points[i] = new Point(x, y);
	    }
	    return points;
	  
  }
  
  // "contains" implements some magical math (i.e. the ray-casting algorithm).
  public boolean contains(Point point) {
    Point[] points = getPoints();
    double crossingNumber = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
           ((points[j].x < point.x) && (point.x <= points[i].x))) &&
          (point.y > points[i].y + (points[j].y-points[i].y)/
           (points[j].x - points[i].x) * (point.x - points[i].x))) {
        crossingNumber++;
      }
    }
    return crossingNumber%2 == 1;
  }
  
  public void rotate(int degrees) {rotation = (rotation+degrees)%360;}
  
  /*
  The following methods are private access restricted because, as this access
  level always implies, they are intended for use only as helpers of the
  methods in this class that are not private. They can't be used anywhere else.
  */
  
  // "findArea" implements some more magic math.
  private double findArea() {
    double sum = 0;
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum += shape[i].x*shape[j].y-shape[j].x*shape[i].y;
    }
    return Math.abs(sum/2);
  }
  
  // "findCenter" implements another bit of math.
  protected Point findCenter() {
    Point sum = new Point(0,0);
    for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
      sum.x += (shape[i].x + shape[j].x)
               * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
      sum.y += (shape[i].y + shape[j].y)
               * (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
    }
    double area = findArea();
    
    return new Point(Math.abs(sum.x/(6*area)),Math.abs(sum.y/(6*area)));
  }
  
  private Point computeCentroid(Point[] pts) {
	    double sumX = 0, sumY = 0;
	    double area = 0;

	    for (int i = 0, j = 1; i < pts.length; i++, j = (i + 1) % pts.length) {
	        double cross = pts[i].x * pts[j].y - pts[j].x * pts[i].y;
	        sumX += (pts[i].x + pts[j].x) * cross;
	        sumY += (pts[i].y + pts[j].y) * cross;
	        area += cross;
	    }

	    area /= 2;
	    return new Point(sumX / (6 * area), sumY / (6 * area));
	}
}