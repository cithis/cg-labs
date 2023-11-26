package ua.kpi.kg3.Algos;

import ua.kpi.kg3.Types.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;

public class SpanFill extends FillAlgorithm {
    protected Color mStartColor;
    protected ArrayDeque<Pixel> mPixels;
    
    public SpanFill(BufferedImage image, int x, int y, Color color) {
        super(image, x, y, color);
        mPixels = new ArrayDeque<>();
        mStartColor = new Color(mImage.getRGB(x, y));
        
        mPixels.offerFirst(new Pixel(x, y));
    }

    @Override
    public FillResult step() {
        if (mPixels.isEmpty())
            return FillResult.FINISHED;
        
        Pixel currentPixel = mPixels.pollFirst();
        int x = currentPixel.x, lx = x, rx = x + 1, y = currentPixel.y;
        while (pixelExists(lx--, y, mStartColor))
            mImage.setRGB(lx + 1, y, mColor.getRGB());
        
        while (pixelExists(rx++, y, mStartColor))
            mImage.setRGB(rx - 1, y, mColor.getRGB());
        
        scan(lx, rx - 1, y + 1);
        scan(lx, rx - 1, y - 1);
        
        return FillResult.CONTINUE;
    }
    
    protected void scan(int lx, int rx, int y) {
        for (int i = lx; i < rx; i++)
            if (pixelExists(i, y, mStartColor))
                mPixels.offerFirst(new Pixel(i, y));
    }
    
    public static Constructor<? extends FillAlgorithm> getConstructor() throws SecurityException, NoSuchMethodException {
        Class[] args = {BufferedImage.class, int.class, int.class, Color.class};
        return SpanFill.class.getConstructor(args);
    }
}
