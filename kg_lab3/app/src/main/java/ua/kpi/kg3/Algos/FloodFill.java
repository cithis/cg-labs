package ua.kpi.kg3.Algos;

import ua.kpi.kg3.Types.Pixel;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.concurrent.LinkedBlockingQueue;

public class FloodFill extends FillAlgorithm {
    protected Color mStartColor;
    protected LinkedBlockingQueue<Pixel> mPixels;
    
    public FloodFill(BufferedImage image, int x, int y, Color color) {
        super(image, x, y, color);
        mStartColor = new Color(mImage.getRGB(x, y));
        mPixels     = new LinkedBlockingQueue<>();
        
        mPixels.offer(new Pixel(x, y));
    }

    @Override
    public FillResult step() {
        if (mPixels.isEmpty())
            return FillResult.FINISHED;
        
        Pixel currentPixel = mPixels.poll();
        int x = currentPixel.x, y = currentPixel.y;
        if (pixelExists(x, y, mStartColor)) {
            // pixel matches start color and should be colored
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
        Class[] args = {BufferedImage.class, int.class, int.class, Color.class};
        return FloodFill.class.getConstructor(args);
    }
}