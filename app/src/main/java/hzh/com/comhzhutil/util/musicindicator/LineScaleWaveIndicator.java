package hzh.com.comhzhutil.util.musicindicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.logging.Logger;


/**
 * Created by huanghua on 2017/9/26.
 */

public class LineScaleWaveIndicator extends BaseIndicatorController {
    public static final float SCALE = 0.4f;

    private Logger mLogger = Logger.getLogger("LineScaleWaveIndicator");

    public boolean isSet=false;
    private long count=0;

    private Float[] indexHeights={-35f,-28f,-20f,-13f};//初始高度
    private Boolean[] isNeedAdds={false,false,false,false};
    private  Integer[] size={0,0,0,0};

    private Float[] bar1={13f,20f,13f,35f};
    private Float[] bar2={13f,25f,13f,30f};
    private Float[] bar3={13f,25f,13f,35f};
    private Float[] bar4={13f,20f,13f,30f};

    private Float[][] bar={bar1,bar2,bar3,bar4};
    private ValueAnimator valueAnimator;

    public LineScaleWaveIndicator(boolean startAni) {
        super();
        if(startAni){
            mCurrentState = START;
            createAnimator();
        }
    }



    private void createAnimator(){
        if(valueAnimator!=null){
            valueAnimator.cancel();
            valueAnimator=null;
        }
        valueAnimator = ValueAnimator.ofFloat(0f,100f);
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                mLogger.info("indecator update"+(float)animation.getAnimatedValue());
                if(count%2==0){
                    if(count==10){
                        count=0;
                    }
                    postInvalidate();
                }
                count++;
            }
        });
        valueAnimator.start();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX = 4;
        float translateY = getHeight();
        for (int i = 0; i < 4; i++) {
            canvas.save();

            canvas.translate(i*4+(i+1)*6, translateY);//设置四根线的摆放位置

                if(indexHeights[i]>-13){
                    isNeedAdds[i]=false;
                }

                if(indexHeights[i]<=-bar[i][size[i]]){
                    isNeedAdds[i]=true;
                    if(size[i]<3){
                        size[i]=size[i]+1;
                    }else {
                        size[i]=0;
                    }

                }

                if(isNeedAdds[i]){
                    indexHeights[i]=indexHeights[i]+1f;
                }else {
                    indexHeights[i]=indexHeights[i]-1f;
                }

                RectF rectF = new RectF(-translateX, indexHeights[i], 0, 0);
                canvas.drawRoundRect(rectF, 2.0f, 2.0f, paint);
                canvas.restore();

        }
    }



    @Override
    public void cancel() {
        isSet=false;
        mCurrentState = CENCLE;
        mLogger.info("valueAnimator cancel------"+ String.valueOf(valueAnimator));
        if(valueAnimator!=null){
            valueAnimator.cancel();
        }

    }

    @Override
    public void end() {
        isSet=false;
        mCurrentState = END;
        if(valueAnimator!=null){
            cancel();
        }
    }

    @Override
    public void restart() {
        mCurrentState = START;
        createAnimator();
    }


}