package hy.com.hyhy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        ListView lv_main = (ListView) findViewById(R.id.lv_main);
        lv_main.setAdapter(new ListViewAdapter(this, list));

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 延迟3秒后刷新成功
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();

                        }
                    }, 2000);
                }
            });


            TextView view = (TextView) findViewById(R.id.tv);
        /*view.setOnTouchListener(new View.OnTouchListener() {
            int startY;
            int offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("listView", "ACTION_DOWN");
                        startY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("listView", "ACTION_MOVE");

                        offsetY  = (int)event.getY() - startY;
                        LinearLayout.LayoutParams mlp =
                                (LinearLayout.LayoutParams) v.getLayoutParams();
                        mlp.topMargin = v.getTop()+offsetY;
                        v.setLayoutParams(mlp);

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("listView", "ACTION_UP");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/

        }

    }
}

