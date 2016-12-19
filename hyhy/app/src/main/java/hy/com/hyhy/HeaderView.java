package hy.com.hyhy;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016-12-17.
 */

public class HeaderView extends LinearLayout {
    LinearLayout header_view;

    public HeaderView(Context context) {
        super(context);
        init(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        header_view = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.header_view,null);
    }

    public  void setHeight(int height){
        LayoutParams layoutParams =(LayoutParams)header_view.getLayoutParams();
        if(layoutParams!=null){
            layoutParams.height=height;
        }else{
            Log.e("setHeight","setHeight");
        }


    }
}
