package ua.kpi.kg3.Algos;

import ua.kpi.kg3.Types.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.concurrent.LinkedBlockingQueue;

public class BoundaryFloodFill extends FillAlgorithm {
    protected Color mBoundaryColor;
    protected LinkedBlockingQueue<Pixel> mPixels;
    
    public BoundaryFloodFill(BufferedImage image, int x, int y, Color color,
            Color boundary) {
        super(image, x, y, color);
        mBoundaryColor = boundary;
        mPixels        = new LinkedBlockingQueue<>();
        
        mPixels.offer(new Pixel(x, y));
    }
    
    @Override
    public FillResult step() {
        if (mPixels.isEmpty())
            return FillResult.FINISHED;
        
        Pixel currentPixel = mPixels.poll();
        int x = currentPixel.x, y = currentPixel.y;
        if (!pixelExists(x, y, mColor) && !pixelExists(x, y, mBoundaryColor)) {
            // pixel isn't part of border and is not already coloured
            mImage.setRGB(x, y, mColor.getRGB());
            
            if (pixelExists(x - 1, y, null))
                mPixels.offer(new Pixel(x - 1, y));
            if (pixelExists(x + 1, y, null))
                mPixels.offer(new Pixel(x + 1, y));
            if (pixelExists(x, y - 1, null))
                mPixels.offer(new Pixel(x, y - 1));
            if (pixelExists(x, y + 1, null))
                mPixels.offer(new Pixel(x, y + 1));
        }
        
        return FillResult.CONTINUE;
    }
    
    public static Constructor<? extends FillAlgorithm> getConstructor() throws SecurityException, NoSuchMethodException {
        Class[] args = {BufferedImage.class, int.class, int.class, Color.class, Color.class};
        return BoundaryFloodFill.class.getConstructor(args);
    }
}