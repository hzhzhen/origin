package hzh.com.comhzhutil.util.aim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import hzh.com.comhzhutil.R;


/**
 */

public class AnimatorHelper {


    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition;
    private RelativeLayout.LayoutParams params;
    private ImageView flyCollectImg;
    private int[] startLoc;
    private int[] endLoc;
    private int[] parentLocation;
    private ValueAnimator mFlyHeartAnimator;
    private final ValueAnimator mCollectAnimator1;
    private final ValueAnimator mCollectAnimator2;
    private final ValueAnimator mCollectAnimator3;
    private final ValueAnimator mCollectAnimator4;
    private final ValueAnimator mCollectAnimator5;
    private final ValueAnimator mCollectAnimator6;
    private final LinearInterpolator interpolator;
    private ValueAnimator mPlayCollectRectAnimator1;
    private ValueAnimator mPlayCollectRectAnimator2;
    private ValueAnimator mPlayCollectRectAnimator3;
    private ValueAnimator mPlayCollectRectAnimator4;
    private ValueAnimator mPlayCollectRectAnimator5;
    private ValueAnimator mPlayCollectRectAnimator6;


    private ValueAnimator mPlayCollectListAnimator1;
    private ValueAnimator mPlayCollectListAnimator2;
    private ValueAnimator mPlayCollectListAnimator3;
    private ValueAnimator mPlayCollectListAnimator4;
    private ValueAnimator mPlayCollectListAnimator5;
    private ValueAnimator mPlayCollectListAnimator6;


    private CollectHeartAnimatorEnd collectHeartAnimatorEnd;
    private ValueAnimator alphaOutAnimator;
    private ObjectAnimator playAlphaComeAnimator;
//    private OndrawListener listener;

    public interface OndrawListener{
        void draw(float x1, float y1, float x2, float y2, float startX, float startY);
    }
    public interface CollectHeartAnimatorEnd{
       void end();
    }


    public AnimatorHelper(Context context){
       //初始化
        mPathMeasure = new PathMeasure();
        mCurrentPosition = new float[2];
        params = new RelativeLayout.LayoutParams(RelativeLayout
        .LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        startLoc = new int[2];
        endLoc = new int[2];
        parentLocation = new int[2];
        mCollectAnimator1 = ValueAnimator.ofFloat(1f,0.94f);
        mCollectAnimator2 = ValueAnimator.ofFloat(0.94f,1.16f);
        mCollectAnimator3 = ValueAnimator.ofFloat(1.16f,0.92f);
        mCollectAnimator4 = ValueAnimator.ofFloat(0.92f,1.03f);
        mCollectAnimator5 = ValueAnimator.ofFloat(1.03f,0.98f);
        mCollectAnimator6 = ValueAnimator.ofFloat(0.98f,1f);

        mCollectAnimator1.setDuration(66);
        mCollectAnimator2.setDuration(100);
        mCollectAnimator3.setDuration(200);
        mCollectAnimator4.setDuration(133);
        mCollectAnimator5.setDuration(166);
        mCollectAnimator6.setDuration(166);


        interpolator = new LinearInterpolator();
        mCollectAnimator1.setInterpolator(interpolator);
        mCollectAnimator2.setInterpolator(interpolator);
        mCollectAnimator3.setInterpolator(interpolator);
        mCollectAnimator4.setInterpolator(interpolator);
        mCollectAnimator5.setInterpolator(interpolator);
        mCollectAnimator6.setInterpolator(interpolator);


        mPlayCollectRectAnimator1 = ValueAnimator.ofFloat(1f,0.92f);
        mPlayCollectRectAnimator2 = ValueAnimator.ofFloat(0.92f,1.16f);
        mPlayCollectRectAnimator3 = ValueAnimator.ofFloat(1.16f,0.91f);
        mPlayCollectRectAnimator4 = ValueAnimator.ofFloat(0.92f,1.00f);
        mPlayCollectRectAnimator5 = ValueAnimator.ofFloat(1.00f,0.98f);
        mPlayCollectRectAnimator6 = ValueAnimator.ofFloat(0.98f,1f);

        mPlayCollectRectAnimator1.setDuration(66);
        mPlayCollectRectAnimator2.setDuration(100);
        mPlayCollectRectAnimator3.setDuration(200);
        mPlayCollectRectAnimator4.setDuration(133);
        mPlayCollectRectAnimator5.setDuration(166);
        mPlayCollectRectAnimator6.setDuration(166);

        mPlayCollectListAnimator1= ValueAnimator.ofFloat(1f,0.92f);
        mPlayCollectListAnimator2= ValueAnimator.ofFloat(0.92f,1.16f);
        mPlayCollectListAnimator3= ValueAnimator.ofFloat(1.16f,0.91f);
        mPlayCollectListAnimator4= ValueAnimator.ofFloat(0.91f,1f);
        mPlayCollectListAnimator5= ValueAnimator.ofFloat(1f,0.98f);
        mPlayCollectListAnimator6= ValueAnimator.ofFloat(0.98f,1f);

        mPlayCollectListAnimator1.setDuration(66);
        mPlayCollectListAnimator2.setDuration(100);
        mPlayCollectListAnimator3.setDuration(200);
        mPlayCollectListAnimator4.setDuration(133);
        mPlayCollectListAnimator5.setDuration(166);
        mPlayCollectListAnimator6.setDuration(166);


    mPlayCollectListAnimator1.setInterpolator(interpolator);
    mPlayCollectListAnimator2.setInterpolator(interpolator);
    mPlayCollectListAnimator3.setInterpolator(interpolator);
    mPlayCollectListAnimator4.setInterpolator(interpolator);
    mPlayCollectListAnimator5.setInterpolator(interpolator);
    mPlayCollectListAnimator6.setInterpolator(interpolator);





        mPlayCollectRectAnimator1.setInterpolator(interpolator);
        mPlayCollectRectAnimator2.setInterpolator(interpolator);
        mPlayCollectRectAnimator3.setInterpolator(interpolator);
        mPlayCollectRectAnimator4.setInterpolator(interpolator);
        mPlayCollectRectAnimator5.setInterpolator(interpolator);
        mPlayCollectRectAnimator6.setInterpolator(interpolator);

    }

//    public void  setDrawListener(OndrawListener listener){
//        this.listener=listener;
//    }
    //收藏飞心动画
    public void buildCollectAnimator(final Context context, final RelativeLayout root, View start, View end, final View heart) {
        flyCollectImg = new ImageView(context);
        flyCollectImg.setImageResource(R.drawable.audio_collect);
        root.addView(flyCollectImg, params);
        start.getLocationInWindow(startLoc);
        end.getLocationInWindow(endLoc);
        root.getLocationInWindow(parentLocation);
        //起始点的确认
        float startX = startLoc[0] - parentLocation[0] + start.getWidth() / 2-10;
        float startY = startLoc[1] - parentLocation[1] + start.getHeight() / 2-20;
        //结束点的确认
        float toX = endLoc[0] - parentLocation[0] + end.getWidth() / 5+10;
        float toY = endLoc[1] - parentLocation[1];
        //四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        float x1=(startX + toX) / 2;
        float y1=(startY-80);//越高弧度应该是越高
        float x2=toX;
        float y2=toY;
        //x1,y1控制点
        path.quadTo(x1,y1, x2, y2);
//        listener.draw (x1,y1,x2,y2,startX,startY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure.setPath(path,false);
        final float pathLength=mPathMeasure.getLength();
        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        if(mFlyHeartAnimator==null){
            mFlyHeartAnimator = ValueAnimator.ofFloat(0, pathLength);
        }
        mFlyHeartAnimator.setDuration(333);
        // 匀速线性插值器
        mFlyHeartAnimator.setInterpolator(new LinearInterpolator());
        mFlyHeartAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                flyCollectImg.setTranslationX(mCurrentPosition[0]);
                flyCollectImg.setTranslationY(mCurrentPosition[1]);
                float scale=1f-(value*0.8f/pathLength*1f);
                //0-length ,1f-0.2f
                flyCollectImg.setScaleY(scale);
                flyCollectImg.setScaleX(scale);
            }
        });
//      五、 开始执行动画
        mFlyHeartAnimator.start();
        mFlyHeartAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
                root.removeView(flyCollectImg);
                playCollectListHeart(heart);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
//     AsyncPlayer asyncPlayer= new AsyncPlayer();


    //播放动画
    public void playCollectAim(View view){
//        String filePath = "android.resource://"+view.getContext().getPackageName()+"/"+ R.raw.bubble;
//        Uri audioUri = Uri.parse(filePath);
//        asyncPlayer.playMusicSoundAsync(view.getContext(), audioUri, false);
        animatorUpdateListener.setView(view);
        mCollectAnimator1.addUpdateListener(animatorUpdateListener);

        mCollectAnimator2.addUpdateListener(animatorUpdateListener);

        mCollectAnimator3.addUpdateListener(animatorUpdateListener);

        mCollectAnimator4.addUpdateListener(animatorUpdateListener);

        mCollectAnimator5.addUpdateListener(animatorUpdateListener);

        mCollectAnimator6.addUpdateListener(animatorUpdateListener);


        mCollectAnimator1.addListener(new CollectAimListener() {
            @Override
            void end() {
                mCollectAnimator2.start();
            }
        });



        mCollectAnimator2.addListener(new CollectAimListener() {
            @Override
            void end() {
                mCollectAnimator3.start();
            }
        });


        mCollectAnimator3.addListener(new CollectAimListener() {
            @Override
            void end() {
            mCollectAnimator4.start();
            }
        });



        mCollectAnimator4.addListener(new CollectAimListener() {
            @Override
            void end() {
            mCollectAnimator5.start();
            }
        });


        mCollectAnimator5.addListener(new CollectAimListener() {
            @Override
            void end() {
                mCollectAnimator6.start();
            }
        });

        mCollectAnimator6.addListener(new CollectAimListener() {
            @Override
            void end() {
               if(collectHeartAnimatorEnd!=null){
                   collectHeartAnimatorEnd.end();
               }
            }
        });
        mCollectAnimator1.start();



    }

    //播放动画
    public void playCollectRectAim( View view){
//        String filePath = "android.resource://"+view.getContext().getPackageName()+"/"+ R.raw.bubble;
//        Uri audioUri = Uri.parse(filePath);
//        asyncPlayer.playMusicSoundAsync(view.getContext(), audioUri, false);
        animatorUpdateListener.setView(view);
        mPlayCollectRectAnimator1.addUpdateListener(animatorUpdateListener);

        mPlayCollectRectAnimator2.addUpdateListener(animatorUpdateListener);

        mPlayCollectRectAnimator3.addUpdateListener(animatorUpdateListener);

        mPlayCollectRectAnimator4.addUpdateListener(animatorUpdateListener);

        mPlayCollectRectAnimator5.addUpdateListener(animatorUpdateListener);

        mPlayCollectRectAnimator6.addUpdateListener(animatorUpdateListener);



        mPlayCollectRectAnimator1.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectRectAnimator2.start();
            }
        });



        mPlayCollectRectAnimator2.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectRectAnimator3.start();
            }
        });


        mPlayCollectRectAnimator3.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectRectAnimator4.start();
            }
        });



        mPlayCollectRectAnimator4.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectRectAnimator5.start();
            }
        });


        mPlayCollectRectAnimator5.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectRectAnimator6.start();
            }
        });


        mPlayCollectRectAnimator6.addListener(new CollectAimListener() {
            @Override
            void end() {

            }
        });

        mPlayCollectRectAnimator1.start();



    }


    interface  AimUpdateListener extends ValueAnimator.AnimatorUpdateListener{
        public void setView(View view);
    }

    private  AimUpdateListener animatorUpdateListener=new AimUpdateListener() {
        public View view;
        public void setView(View view){
            this.view=view;
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Float) animation.getAnimatedValue();
            view.setScaleX(value);
            view.setScaleY(value);
        }
    };

    //播放动画
    private void playCollectListHeart(final View view){
        animatorUpdateListener.setView(view);

        mPlayCollectListAnimator1.addUpdateListener(animatorUpdateListener);

        mPlayCollectListAnimator2.addUpdateListener(animatorUpdateListener);

        mPlayCollectListAnimator3.addUpdateListener(animatorUpdateListener);

        mPlayCollectListAnimator4.addUpdateListener(animatorUpdateListener);

        mPlayCollectListAnimator5.addUpdateListener(animatorUpdateListener);

        mPlayCollectListAnimator6.addUpdateListener(animatorUpdateListener);



        mPlayCollectListAnimator1.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectListAnimator2.start();
            }
        });



        mPlayCollectListAnimator2.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectListAnimator3.start();
            }
        });


        mPlayCollectListAnimator3.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectListAnimator4.start();
            }
        });



        mPlayCollectListAnimator4.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectListAnimator5.start();
            }
        });


        mPlayCollectListAnimator5.addListener(new CollectAimListener() {
            @Override
            void end() {
                mPlayCollectListAnimator6.start();
            }
        });

        mPlayCollectListAnimator6.addListener(new CollectAimListener() {
            @Override
            void end() {
                playAlphaOut(view);
            }
        });
        mPlayCollectListAnimator1.start();



    }



    public void playAlphaCome(View view){
        if(playAlphaComeAnimator==null){
            playAlphaComeAnimator = ObjectAnimator.ofFloat(view,"alpha",0f, 1f);
            playAlphaComeAnimator.setDuration(233);
        }
        playAlphaComeAnimator.start();
    }


    private void playAlphaOut(View view){
        if(alphaOutAnimator ==null){
            alphaOutAnimator = ObjectAnimator.ofFloat(view,"alpha",1f, 0f);
            alphaOutAnimator.setDuration(233);
        }
        alphaOutAnimator.start();

    }



    abstract  class CollectAimListener implements Animator.AnimatorListener{

        abstract  void end();

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            end();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
    public void setCollectHeartAnimatorEnd(CollectHeartAnimatorEnd end){
        this.collectHeartAnimatorEnd=end;
    }

}
