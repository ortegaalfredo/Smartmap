package net.sourceforge.jmicropolygon;

import java.util.Stack;

import javax.microedition.lcdui.Graphics;

/**
 * Polygon rendering for J2ME MIDP 1.0.
 *
 * <p>This has its own fillTriangle() method because of the 
 * absence of that method in MIDP 1.0 (unlike MIDP 2.0). </p>
 *
 * @author <a href="mailto:simonturner@users.sourceforge.net">Simon Turner</a> 
 * @version $Id: PolygonGraphics.java,v 1.1.1.1.2.3 2007/02/27 21:40:48 simonturner Exp $
 */
public class PolygonGraphics {

    /**
     * Draw a polygon
     *
     * @param g         The Graphics object to draw the polygon onto
     * @param xPoints   The x-points of the polygon
     * @param yPoints   The y-points of the polygon
     */
    public static void drawPolygon(Graphics g, int[] xPoints, int[] yPoints) {
        int max = xPoints.length - 1;
        for (int i=0; i<max; i++) {
            g.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
        }
        g.drawLine(xPoints[max], yPoints[max], xPoints[0], yPoints[0]);
    }

    /**
     * Fill a polygon
     *
     * @param g         The Graphics object to draw the polygon onto
     * @param xPoints   The x-points of the polygon
     * @param yPoints   The y-points of the polygon
     */
    public static void fillPolygon(Graphics g, int[] xPoints, int[] yPoints) {
    	Stack stack = new Stack();
    	fillPolygon(g, xPoints, yPoints, stack);
    	while (!stack.isEmpty()) {
    	    fillPolygon(g, (int[]) stack.pop(), (int[]) stack.pop(), stack);
    	}
    }
    
    /**
     * Fill a polygon
     *
     * @param g         The Graphics object to draw the polygon onto
     * @param xPoints   The x-points of the polygon
     * @param yPoints   The y-points of the polygon
     * @param stack		The Stack
     */
    private static void fillPolygon(Graphics g, int[] xPoints, int[] yPoints, Stack stack) {
        while (xPoints.length > 2) {
            // a, b & c represents a candidate triangle to draw. 
            // a is the left-most point of the polygon
            int a = GeomUtils.indexOfLeast(xPoints);
            // b is the point after a
            int b = (a + 1) % xPoints.length;
            // c is the point before a
            int c = (a > 0) ? a - 1 : xPoints.length - 1;
            // The value leastInternalIndex holds the index of the left-most 
            // polygon point found within the candidate triangle, if any.
            int leastInternalIndex = -1;
            boolean leastInternalSet = false;
            // If only 3 points in polygon, skip the tests
            if (xPoints.length > 3) {
                // Check if any of the other points are within the candidate triangle
                for (int i=0; i<xPoints.length; i++) {
                    if (i != a && i != b && i != c) {
                        if (GeomUtils.withinBounds(xPoints[i], yPoints[i], 
                                                   xPoints[a], yPoints[a],
                                                   xPoints[b], yPoints[b],
                                                   xPoints[c], yPoints[c])) {
                            // Is this point the left-most point within the candidate triangle?
                            if (!leastInternalSet || xPoints[i] < xPoints[leastInternalIndex]) {
                                leastInternalIndex = i;
                                leastInternalSet = true;
                            }
                        }
                    }
                }
            }
            // No internal points found, fill the triangle, and reservoir-dog the polygon
            if (!leastInternalSet) {
                fillTriangle(g, xPoints[a], yPoints[a], xPoints[b], yPoints[b], xPoints[c], yPoints[c]);
                int[][] trimmed = GeomUtils.trimEar(xPoints, yPoints, a);
                xPoints = trimmed[0];
                yPoints = trimmed[1];
            // Internal points found, split the polygon into two, using the line between
            // "a" (left-most point of the polygon) and leastInternalIndex (left-most  
            // polygon-point within the candidate triangle) and recurse with each new polygon
            } else {
                int[][][] split = GeomUtils.split(xPoints, yPoints, a, leastInternalIndex);
                int[][] poly1 = split[0];
                int[][] poly2 = split[1];
//                fillPolygon(g, poly1[0], poly1[1]);
//                fillPolygon(g, poly2[0], poly2[1]);
                stack.push(poly2[1]);
        		stack.push(poly2[0]);
        		stack.push(poly1[1]);
        		stack.push(poly1[0]);
                break;
            }
        }
    }

    /**
     * This required for MIDP 1.0 only.
     *
     * See <a href="http://forum.java.sun.com/thread.jsp?forum=20&thread=515096&start=0&range=15&tstart=0&trange=15">here</a>
     */
    public static void fillTriangle(Graphics g, int x0, int y0, int x1, int y1, int x2, int y2) {

        g.fillTriangle(x0, y0, x1, y1, x2, y2);
        /*
        int[] left = new int[2000];    // store the x value of the pixel on 
                                       // the left side of the triangle at a given y value.
        int[] right = new int[2000];   // ditto for the right side

        int yMin = y0;
        int yMax = y0;
        if(y1 < yMin) yMin = y1;
        else if(y1 > yMax) yMax = y1;
        if(y2 < yMin) yMin = y2;
        else if(y2 > yMax) yMax = y2;
        
        // use the fact that the vertices are in counterclockwise order to determine whether
        // the edges are left or right edges.
        getLineXVals(x0, y0, x1, y1, (y0>y1 ? left : right));
        getLineXVals(x1, y1, x2, y2, (y1>y2 ? left : right));
        getLineXVals(x0, y0, x2, y2, (y2>y0 ? left : right));
        
        for(int y = yMin; y < yMax; y++) {
            int l = left[y];
            int r = right[y];
            int w = r - l;
            if(w<0) continue;
            g.drawLine(l, y, r, y);
        }
         */
    }

    /**
     * Given a line, calculate its x coordinates for each y value and place them into dest.
     * 
     * See <a href="http://forum.java.sun.com/thread.jsp?forum=20&thread=515096&start=0&range=15&tstart=0&trange=15">here</a>
     */
    private static void getLineXVals(int x1, int y1, int x2, int y2, int[] dest) {
        if(y1 > y2) {     // swap so y1 is on top
            int t = x1; x1 = x2; x2 = t;
            t = y1; y1 = y2; y2 = t;
        }
        else if(y1 == y2) {     // horizontal
            return;
        }
        int dx, dy;
        dy = y2 - y1;
        dx = x2 - x1;
        int gi = (dx*2048) / dy;          // multiplies and divides by 2048 should get optimized to shifts by the compiler.
        int xi = (x1*2048);
        for(int y = y1; y < y2; y++) {
            dest[y] = (xi/2048);
            xi += gi;
        }
    }

}