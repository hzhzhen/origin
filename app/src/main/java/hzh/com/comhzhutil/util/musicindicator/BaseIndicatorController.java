package hzh.com.comhzhutil.util.musicindicator;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by huanghua on 2017/9/26.
 */

public abstract class BaseIndicatorController {
    private WeakReference<View> mTarget;

    private Logger mLogger = Logger.getLogger("BaseIndicatorController");

    private List<Animator> mAnimators;

    public static final String START = "START";
    public static final String CENCLE = "CENCLE";
    public static final String END = "END";

    protected String mCurrentState = START;

    public void setTarget(View target) {
        this.mTarget = new WeakReference<>(target);
    }

    public View getTarget() {
        return mTarget != null ? mTarget.get() : null;
    }


    public int getWidth() {
        return getTarget() != null ? getTarget().getWidth() : 0;
    }

    public int getHeight() {
        return getTarget() != null ? getTarget().getHeight() : 0;
    }

    public void postInvalidate() {
        if (getTarget() != null) {
            getTarget().postInvalidate();//刷新界面
        }
    }

    /**
     * draw indicator
     *
     * @param canvas
     * @param paint
     */
    public abstract void draw(Canvas canvas, Paint paint);


    public abstract void cancel();

    public abstract void end();

    public abstract void restart();

    /**
     * make animation to start or end when target
     * view was be Visible or Gone or Invisible.
     * make animation to cancel when target view
     * be onDetachedFromWindow.
     *
     * @param animStatus
     */
    public void setAnimationStatus(AnimStatus animStatus) {
        mLogger.info("setAnimationStatus +"+ String.valueOf(animStatus));
            switch (animStatus) {
                case END:
                    end();
                    break;
                case CANCEL:
                    cancel();
                    break;
                case RESTART:
                    restart();
                    break;
            }

    }

    public String getState() {
        return mCurrentState;
    }


    public enum AnimStatus {
        RESTART, END, CANCEL
    }


}

