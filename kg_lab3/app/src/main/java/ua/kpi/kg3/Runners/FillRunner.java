package ua.kpi.kg3.Runners;

import ua.kpi.kg3.Algos.FillResult;
import ua.kpi.kg3.Algos.IFillAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class FillRunner implements Runnable {
    protected JLabel mStatusLabel, mOpCountLabel, mTimeLabel, mHelpLabel;
    protected int mOpsLimit, mFlushPeriod;
    protected Color mFillColor;
    protected BufferedImage mInternalCanvas;
    protected JLabel mContainer;

    public FillRunner() {
        mStatusLabel    = null;
        mOpCountLabel   = null;
        mTimeLabel      = null;
        mHelpLabel      = null;
        mFillColor      = null;
        mOpsLimit       = 60;
        mFlushPeriod    = -1;
        mContainer      = null;
        mInternalCanvas = new BufferedImage(1000, 700,
                BufferedImage.TYPE_INT_ARGB_PRE);
    }

    public void setStatusLabel(JLabel StatusLabel) {
        mStatusLabel = StatusLabel;
    }

    public void setOpCountLabel(JLabel OpCountLabel) {
        mOpCountLabel = OpCountLabel;
    }

    public void setTimeLabel(JLabel TimeLabel) {
        mTimeLabel = TimeLabel;
    }

    public void setHelpLabel(JLabel HelpLabel) {
        mHelpLabel = HelpLabel;
    }

    public void setFillColor(Color FillColor) {
        mFillColor = FillColor;
    }

    public void setOpsLimit(int OpsLimit) {
        mOpsLimit = OpsLimit;
    }

    public void setFlushPeriod(int FlushPeriod) {
        mFlushPeriod = FlushPeriod;
    }

    public void setContainer(JLabel container) {
        mContainer = container;
    }

    protected boolean isNotReady() {
        return mStatusLabel  == null
            || mOpCountLabel == null
            || mTimeLabel    == null
            || mHelpLabel    == null
            || mFillColor    == null
            || mContainer    == null
            || mOpsLimit     < -1
            || mFlushPeriod  < -2;
    }

    protected void flush() {
        mContainer.setText("");
        mContainer.setIcon(new ImageIcon(mInternalCanvas));
        mContainer.repaint();
    }

    public void prepareCanvas() {
        mInternalCanvas.getGraphics()
                .clearRect(0, 0, 1000, 700);
        flush();
    }

    public void clear() {
        prepareCanvas();
    }

    protected void osDelay(long delayNanos) {
        long base = System.nanoTime(), diff;
        while ((diff = System.nanoTime() - base) <= delayNanos);
        System.out.println("[osDelay] requested delay " + delayNanos + "ns, actual="
                + diff + "ns");
    }

    protected abstract IFillAlgorithm getFillImpl();

    @Override
    public void run() {
        if (isNotReady()) {
            System.out.println("[ImgRunner] run: runner not ready");
            return;
        }

        IFillAlgorithm fillImpl = getFillImpl();
        if (fillImpl == null)
            return;

        long timePassed = 0;
        long nsPerOp = 0, opsCount = 0;
        if (mOpsLimit != -1)
            nsPerOp = (int) Math.floor((double) 1000000000L / mOpsLimit);

        int opsPerFlush = mFlushPeriod;
        if (opsPerFlush == -2)
            opsPerFlush = mOpsLimit;

        System.out.println("[ImgRunner] nsPerOp=" + nsPerOp + " opsPerFlush="
                + opsPerFlush);

        mStatusLabel.setText("виконується");
        FillResult result = FillResult.CONTINUE;
        while (result == FillResult.CONTINUE) {
            long timeStart = System.nanoTime();
            result         = fillImpl.step();
            long timeEnd   = System.nanoTime();
            long timeDiff  = timeEnd - timeStart;

            opsCount++;
            timePassed += timeDiff;

            long timeSurplus = nsPerOp - timeDiff;

            // We also must account for UI repainting time in order
            // to cap algorithm speed correctly.
            long timeBeforeUiUpdate = System.nanoTime();

            if (opsPerFlush != -1)
                if (opsPerFlush == 0 || (opsCount % opsPerFlush) == 0)
                    flush();

            mOpCountLabel.setText(Long.toString(opsCount));
            mTimeLabel.setText((int) (timePassed / 1000000) + "ms");

            long timeDiffUi = System.nanoTime() - timeBeforeUiUpdate;
            timeSurplus    -= timeDiffUi;

            if (timeSurplus > 0)
                osDelay(timeSurplus);
        }

        if (result == FillResult.ERROR)
            mStatusLabel.setText("помилка");
        else
            mStatusLabel.setText("завершено");

        flush();
    }
}
