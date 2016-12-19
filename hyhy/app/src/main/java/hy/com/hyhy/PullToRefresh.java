package hy.com.hyhy;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016-12-17.
 */

public class PullToRefresh extends LinearLayout {
private  Context mContext;
    public PullToRefresh(Context context) {
        super(context);
        mContext= context;

    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext= context;

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private int startY;

    LinearLayout  headerView;
    int  offsetY;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("dispatchTouchEvent","ACTION_DOWN,ev.getY()="+ev.getY());
                startY =  (int)ev.getY();
            break;
            case MotionEvent.ACTION_MOVE:
                offsetY  = (int)ev.getY() - startY;
                LinearLayout.LayoutParams mlp = (LayoutParams) getLayoutParams();
                mlp.topMargin = getTop() + offsetY;
                setLayoutParams(mlp);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("dispatchTouchEvent","ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }





    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("onInterceptTouchEvent","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("onInterceptTouchEvent","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("onInterceptTouchEvent","ACTION_UP");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("onTouchEvent","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("onTouchEvent","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("onTouchEvent","ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }

}
