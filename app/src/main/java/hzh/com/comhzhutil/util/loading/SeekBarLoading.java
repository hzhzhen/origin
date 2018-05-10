package hzh.com.comhzhutil.util.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


import java.util.logging.Logger;

import hzh.com.comhzhutil.R;

/**
 * Created by huanghua on 2017/10/24.
 */

public class SeekBarLoading extends View {
    private static final Logger LOGGER  =   Logger.getLogger("SeekBarLoading");
    private Paint p;
    private Path path;
    Handler handler=new Handler(Looper.getMainLooper());


    private int COLOR_WRITE_20;


    private Bitmap mBitmap;
    private Bitmap mBitmap2;

    //无参
    public SeekBarLoading(Context context) {
        super(context);
        initAll(context);
    }

    //有参
    public SeekBarLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAll(context);

    }

    private void initAll(Context context) {
        COLOR_WRITE_20= ContextCompat.getColor(context, R.color.white_20);
        p = new Paint();
        setBackgroundColor(COLOR_WRITE_20);
        path=new Path();
        mBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.loading_bar)).getBitmap();
        mBitmap2 = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.loading_bar))
                   .getBitmap();

    }

    private  boolean isSet=false;


    float drawLeft1 = 0;
    float drawLeft2 = -1024;

    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mBitmap2==null||mBitmap2.isRecycled()||mBitmap==null||mBitmap.isRecycled()){
            return;
        }
          path.reset();
        canvas.drawBitmap(mBitmap, drawLeft1,0,p);//当移动到1024时候   从-1024 走
        canvas.drawBitmap(mBitmap2, drawLeft2,0,p);

        if(drawLeft1==1024){
            //到了临界点
            drawLeft1= -1024;
        }

        if(drawLeft2 ==1024){
            drawLeft2 = -1024;
        }
        drawLeft1 = drawLeft1+2f;
        drawLeft2 = drawLeft2 +2f;
        LOGGER.info("loading draw");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);



    }

    public void recycleBitMap(){

        if(mBitmap!=null&&!mBitmap.isRecycled()){
            mBitmap.recycle();
        }
        if(mBitmap2!=null&&!mBitmap2.isRecycled()){
            mBitmap2.recycle();
        }


    }
}
