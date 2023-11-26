package ua.kpi.kg3.Types;

import java.util.ArrayList;

public class Polygon {
    protected ArrayList<Pixel> mVertices;
    
    public Polygon() {
        mVertices = new ArrayList<>();
    }
    
    public int getVerticesCount() {
        return mVertices.size();
    }
    
    public Pixel getVertex(int i) {
        return mVertices.get(i);
    }
    
    public void addVertex(int x,int y) {
        mVertices.add(new Pixel(x, y));
    }
    
    public void removeVertex() {
        if (getVerticesCount() != 0)
            mVertices.remove(getVerticesCount() - 1);
    }
}