package hzh.com.comhzhutil.util.musicindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by huanghua on 2017/10/12.
 */

public class LineSpaceExtraContainer extends ViewGroup {
    public LineSpaceExtraContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View view = getChildAt(0);

        view.measure(widthMeasureSpec, heightMeasureSpec);
//        //总高度减去多余的行间距高作为该容器的高
//        if(((TextView)getChildAt(0)).getLineCount()>1&&((TextView)getChildAt(0)).getLineCount()!=2){
//            setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight()-28);
//            return;
//        }
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (getChildCount() < 1) {
            throw new IllegalStateException("must has one child view");
        }

        getChildAt(0).layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }
}
