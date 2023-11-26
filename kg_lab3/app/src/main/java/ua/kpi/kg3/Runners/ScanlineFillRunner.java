package ua.kpi.kg3.Runners;

import ua.kpi.kg3.Algos.IFillAlgorithm;
import ua.kpi.kg3.Algos.Scanline.ScanlineFill;
import ua.kpi.kg3.Types.Pixel;
import ua.kpi.kg3.Types.Polygon;

import java.awt.*;

public class ScanlineFillRunner extends FillRunner {
    protected Polygon mPoly;

    public ScanlineFillRunner() {
        super();
        mPoly = new Polygon();
    }

    protected void repaintPolygon() {
        clear();
        if (mPoly.getVerticesCount() < 3)
            return;

        Graphics g = mInternalCanvas.getGraphics();
        g.setColor(mFillColor);
        for (int count = mPoly.getVerticesCount(), i = 0; i < count; i++) {
            Pixel a = mPoly.getVertex(i),
                  b = mPoly.getVertex((i + 1) % count);

            g.drawLine(a.x, a.y, b.x, b.y);
            System.out.println("[PolyRenderer] edge: " + a.x + ", " + a.y + " to " + b.x + ", " + b.y);
        }

        flush();
    }

    public boolean isNotReady() {
        return super.isNotReady() || mPoly.getVerticesCount() < 3;
    }

    public void addVertex(int x, int y) {
        mPoly.addVertex(x, y);
        repaintPolygon();
    }

    public void removeVertex() {
        mPoly.removeVertex();
        repaintPolygon();
    }

    public boolean verticesLeft() {
        return mPoly.getVerticesCount() > 0;
    }

    @Override
    protected IFillAlgorithm getFillImpl() {
        return new ScanlineFill(mInternalCanvas, mPoly, mFillColor);
    }
}
