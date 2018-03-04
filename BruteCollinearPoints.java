import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
{
    private Point[] copies;
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        assertPoints(points);
        int N = points.length;
        copies = new Point[N];
        for (int i = 0; i < N; i++)
        { copies[i] = points[i]; }
        Arrays.sort(copies);
       
        for (int i = 0; i < N-3; i++)
        {
            for (int j = i+1; j < N-2; j++)
            {
                for (int k =  j+1; k < N-1; k++)
                {
                    if (copies[i].slopeTo(copies[j]) != copies[i].slopeTo(copies[k])) continue;
                    else
                    {
                        for (int m = k+1; m < N; m++)
                        {
                            if (copies[i].slopeTo(copies[j]) != copies[i].slopeTo(copies[m])) continue;
                            else segs.add(new LineSegment(copies[i], copies[m]));
                        }
                    }      
                }
            }
        }
    }
   
    public int numberOfSegments()        // the number of line segments
    {
        return segs.size();
    }
    
    public LineSegment[] segments()                // the line segments
    { 
        LineSegment[] segments;
        segments = segs.toArray(new LineSegment[segs.size()]);
        return segments; 
    }
    
    private void assertPoints(Point[] points)
    {
        if (points == null) throw new IllegalArgumentException("Argument cannot be null");
        int N = points.length;
        for (int i = 0; i < N; i++)
        { if (points[i] == null) throw new IllegalArgumentException("Argument cannot contain null element"); }
        for (int i = 0; i < N-1; i++)
        {
            for (int j = i+1; j < N; j++)
            { if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Argument cannot contain duplicate points"); }
        }
    }  
    
    public static void main(String[] args) 
    {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }  
}