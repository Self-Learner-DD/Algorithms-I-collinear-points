import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    private final int x;
    private final int y;
    
    public Point(int x, int y)                         // constructs the point (x, y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw()                               // draws this point
    { 
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x, y); 
    }
        
    public void drawTo(Point that)                   // draws the line segment from this point to that point
    { StdDraw.line(this.x, this.y, that.x, that.y); }
    
    public String toString()                           // string representation
    { return "(" + x + ", " + y + ")"; }

    public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    { 
        if (this.y > that.y) return +1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return +1;
        if (this.x < that.x) return -1;
        return 0; //no need to use else
    }
        
    public double slopeTo(Point that)       // the slope between this point and that point
    {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) return +0;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        return 1.0*(that.y-this.y)/(that.x-this.x);
    }
    
    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
    { return new SlopeOrder(); }
    
    private class SlopeOrder implements Comparator<Point>
    { 
        public int compare(Point v, Point w)
        { 
            double slopeDiff = slopeTo(v) - slopeTo(w);
            if (slopeDiff > 0) return +1;
            if (slopeDiff < 0) return -1;
            return 0;
        }
    }
}