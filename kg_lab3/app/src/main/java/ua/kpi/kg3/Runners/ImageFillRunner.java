package ua.kpi.kg3.Runners;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import ua.kpi.kg3.Algos.FillAlgorithm;
import ua.kpi.kg3.Algos.FillResult;
import ua.kpi.kg3.Algos.IFillAlgorithm;

public class ImageFillRunner extends FillRunner {
    protected Color mBorderColor;
    protected Constructor<? extends FillAlgorithm> mFillImplCtor;
    protected int mStartX, mStartY;

    public ImageFillRunner() {
        super();
        mFillImplCtor = null;
        mBorderColor  = null;
    }

    public void setBorderColor(Color BorderColor) {
        mBorderColor = BorderColor;
    }

    public void setFillImplClass(Constructor<? extends FillAlgorithm> FillImplCtor) {
        mFillImplCtor = FillImplCtor;
    }

    public void setStartingPixel(int x, int y) {
        mStartX = x;
        mStartY = y;
    }
    
    public boolean setImage(String filename) {
        if (isNotReady()) {
            System.out.println("[ImgRunner] setImage: runner not ready");
            return false;
        }
        
        try {
            BufferedImage image = ImageIO.read(new File(filename));
            mInternalCanvas.getGraphics().drawImage(image, 0, 0, null);
            flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public boolean isNotReady() {
        return super.isNotReady()
                || mFillImplCtor == null
                || mStartX        < 0
                || mStartY        < 0;
    }

    @Override
    protected IFillAlgorithm getFillImpl() {
        FillAlgorithm fillImpl;
        try {
            if (mBorderColor == null) {
                fillImpl = mFillImplCtor.newInstance(mInternalCanvas,
                        mStartX, mStartY, mFillColor);
            } else {
                fillImpl = mFillImplCtor.newInstance(mInternalCanvas,
                        mStartX, mStartY, mFillColor,
                        mBorderColor);
            }
        } catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException ex) {
            mStatusLabel.setText("помилка творення");
            ex.printStackTrace();
            return null;
        }

        return fillImpl;
    }
}