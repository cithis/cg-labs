package ua.kpi.kg3.Algos;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.lang.reflect.Constructor;

public abstract class FillAlgorithm implements IFillAlgorithm {
    protected BufferedImage mImage;
    protected int mStartX, mStartY;
    protected Color mColor;
    
    public FillAlgorithm(BufferedImage image, int x, int y, Color color) {
        mImage  = image;
        mStartX = x;
        mStartY = y;
        mColor  = color;
    }
    
    public abstract FillResult step();
    
    public static Constructor<? extends FillAlgorithm> getConstructor() throws SecurityException, NoSuchMethodException {
        throw new UnsupportedOperationException("meow");
    }
    
    protected boolean pixelExists(int x, int y, Color color) {
        boolean exists = y >= 0 && y < mImage.getHeight() && x >= 0
                && x < mImage.getWidth();
        
        if(color != null)
            exists = exists && mImage.getRGB(x, y) == color.getRGB();
        
        return exists;
    }
}