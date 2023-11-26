package ua.kpi.kg3.Algos.Scanline;

public class PolygonEdge {
    public int yMin, yMax;
    public double x, slope;

    public PolygonEdge(int yMin, int yMax, double x, double slope) {
        this.yMin  = yMin;
        this.yMax  = yMax;
        this.x     = x;
        this.slope = slope;
    }
}