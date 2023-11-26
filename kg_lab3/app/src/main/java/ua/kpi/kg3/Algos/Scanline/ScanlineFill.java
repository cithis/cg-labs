package ua.kpi.kg3.Algos.Scanline;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ua.kpi.kg3.Algos.FillResult;
import ua.kpi.kg3.Algos.IFillAlgorithm;
import ua.kpi.kg3.Types.Pixel;
import ua.kpi.kg3.Types.Polygon;

public class ScanlineFill implements IFillAlgorithm {
    protected BufferedImage mImage;
    protected ArrayList<PolygonEdge> mEdges;
    protected int mMinY, mMaxY, mY;
    protected Color mColor;

    public ScanlineFill(BufferedImage image, Polygon poly, Color color) {
        mImage = image;
        mColor = color;
        mEdges = new ArrayList<>();
        
        prepareEdges(poly);
    }
    
    private void prepareEdges(Polygon poly) {
        int count = poly.getVerticesCount();
        for (int i = 0; i < count; i++) {
            Pixel a = poly.getVertex(i), b = poly.getVertex((i + 1) % count);
            int ax  = a.x, bx = b.x, ay = a.y, by = b.y;
            if (ay > by) {
                int tx = ax, ty = ay;
                ax = bx;
                bx = tx;
                ay = by;
                by = ty;
            }
            
            if (ay == by)
                continue;
            
            double slope     = (double) (bx - ax) / (by - ay);
            PolygonEdge edge = new PolygonEdge(ay, by, ax, slope);
            mEdges.add(edge);
        }
        
        mEdges.sort(Comparator.comparingInt((var e) -> e.yMin));
        mMinY = mEdges.get(0).yMin;
        mMaxY = mEdges.get(mEdges.size() - 1).yMax;
        mY    = mMinY;
    }
    
    public FillResult step() {
        if (mY > mMaxY)
            return FillResult.FINISHED;
        
        ArrayList<Integer> intersections = new ArrayList<>();
        for (var edge : mEdges) {
            if (mY < edge.yMin || mY >= edge.yMax)
                continue;
            
            int x = (int) (edge.x + edge.slope * (mY - edge.yMin));
            intersections.add(x);
        }
            
        Collections.sort(intersections);
        for (int i = 0; i < intersections.size(); i += 2) {
            int ax = intersections.get(i);
            int bx = intersections.get(i + 1);
            for (int j = ax; j <= bx; j++)
                mImage.setRGB(j, mY, mColor.getRGB());
        }
        
        mY++;
        return FillResult.CONTINUE;
    }
}