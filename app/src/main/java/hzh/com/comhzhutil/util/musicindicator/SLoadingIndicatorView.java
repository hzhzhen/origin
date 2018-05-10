package hzh.com.comhzhutil.util.musicindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import hzh.com.comhzhutil.R;


/**
 * Created by huanghua on 2017/9/26.
 */

public class SLoadingIndicatorView extends View {
    private static final String TAG = SLoadingIndicatorView.class.getSimpleName();

    private java.util.logging.Logger mLogger = java.util.logging.Logger.getLogger("SLoadingIndicatorView");

    public static int DEFAULT_SIZE = 45;

    private SLoadingIndicatorView self;

    private boolean startAni = false;

    Paint mPaint;
    BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;

    public SLoadingIndicatorView(Context context) {
        super(context);
        init(context,null, 0);
    }

    public SLoadingIndicatorView(Context context, int defaultSize) {
        super(context);
        DEFAULT_SIZE = defaultSize;
        init(context,null, 0);
    }

    public SLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public SLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mLogger.info("init ");
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SLoadingIndicatorView);
        if (ta != null){
            startAni = ta.getBoolean(R.styleable.SLoadingIndicatorView_item_start,false);
            ta.recycle();
        }
        self = this;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#fa8224"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        applyIndicator();

    }

    private void applyIndicator() {
                int w = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
                int h = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
                this.measure(w, h);
                mLogger.info("startAni "+startAni);
                mIndicatorController = new LineScaleWaveIndicator(startAni);
                bringToFront();
        mIndicatorController.setTarget(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE+5), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            //applyAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.END);
            } else {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.RESTART);
            }
        }
    }

    /**
     * onAttachedToWindow是在第一次onDraw前调用的。也就是我们写的View在没有绘制出来时调用的，但只会调用一次。
     * onDetachedFromWindow相反
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if (mHasAnimation) {
//            mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.RESTART);
//        }
    }

    @Override
    public boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    /**
     * This is called when the view is detached from a window. At this point it no longer has a surface for drawing.
     */
    @Override
    protected void onDetachedFromWindow() {
        mIndicatorController.end();
        super.onDetachedFromWindow();
//        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL);
    }

    private boolean hasRegusterRxbus = false;
    public void registerrx(){
        mLogger.info("registerRxbus "+hasRegusterRxbus);
//        if(!hasRegusterRxbus){
//            hasRegusterRxbus = true;
//            RxBus.get().register(this);
//        }

    }

    private boolean hasUnregusterRxbus = false;
    public void unregisterrx(){
        mLogger.info("unregisterRxBus "+hasUnregusterRxbus);
//        if(!hasUnregusterRxbus){
//            hasUnregusterRxbus = true;
//            RxBus.get().unregister(this);
//        }
    }

    void drawIndicator(Canvas canvas) {
        mIndicatorController.draw(canvas, mPaint);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }


    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public void start() {
        mLogger.info("start");
        mPaint.setColor(Color.parseColor("#fa8224"));
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.RESTART);
    }

    public void pause() {
        mLogger.info("pause");
        mPaint.setColor(Color.parseColor("#4dffffff"));
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL);
        invalidate();
    }


}
