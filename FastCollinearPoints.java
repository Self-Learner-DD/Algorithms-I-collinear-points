import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;

// didn't solve problems caused by more than 4 points in a line...
// can solve by use a hashtable, contain the endpoint of each segment as key, and an arraylist of the slopes, before adding new segment, 
// check if the endpoint and slope has already in the hashtable...
// revisit when learn hashtable...

public class FastCollinearPoints 
{
    private Point[] copies;
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
    
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        assertPoints(points);
        int N = points.length;
        copies = new Point[N];
        for (int i = 0; i < N; i++)
        { copies[i] = points[i]; }
        Arrays.sort(copies);
        ST<Point, SET<Double>> st = new ST<Point, SET<Double>>();
        
        for (int i = 0; i < N-3; i++)
        {
            Point origin = copies[i];
            double [] slopes =  new double[N-1-i];
            Point[] others = new Point[N-1-i];
            for (int j = 0; j < N-1-i; j++)
            {
                slopes[j] = origin.slopeTo(copies[i+1+j]);
                others[j] = copies[i+1+j];
            }
            Arrays.sort(others,origin.slopeOrder());
            Arrays.sort(slopes);
            
            int cnt_same = 1;
            for (int j = 0; j < others.length-1; j++)
            {
                if (slopes[j+1] == slopes[j]) 
                {
                    cnt_same++;
                    if ((j+1) != others.length - 1) continue;  // check if reach end...
                    else if (cnt_same >= 3) 
                    {
                        Point p = others[j+1];
                        if (!st.contains(p))
                        { 
                            st.put(p, new SET<Double>());
                            SET<Double> set = st.get(p);
                            set.add(slopes[j+1]);
                            segs.add(new LineSegment(origin, p));
                        }
                        else if (!st.get(p).contains(slopes[j+1]))
                        {
                            st.get(p).add(slopes[j+1]);
                            segs.add(new LineSegment(origin, p));
                        }
                    }
                }
                else if (slopes[j+1] != slopes[j] && cnt_same >= 3) 
                { 
                    Point p = others[j];
                    if (!st.contains(p))
                    { 
                        st.put(p, new SET<Double>());
                        SET<Double> set = st.get(p);
                        set.add(slopes[j]);
                        segs.add(new LineSegment(origin, p));
                    }
                    else if (!st.get(p).contains(slopes[j]))
                    {
                        st.get(p).add(slopes[j]);
                        segs.add(new LineSegment(origin, p));
                    }
                }
                cnt_same = 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }  
}

