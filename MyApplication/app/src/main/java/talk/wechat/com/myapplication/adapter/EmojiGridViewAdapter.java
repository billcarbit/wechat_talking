package talk.wechat.com.myapplication.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import talk.wechat.com.myapplication.R;
import talk.wechat.com.myapplication.bean.TalkingMsg;
import talk.wechat.com.myapplication.media.Voice;

/**
 * Created by Administrator on 2016/11/18.
 */
public class EmojiGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Resources mResources;
    private int[] images = new int[]{R.drawable.icon1, R.drawable.icon2};


    public EmojiGridViewAdapter(Context context) {
        mContext = context;
        mResources = mContext.getResources();
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_emoji_item, null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_icon.setImageDrawable(mResources.getDrawable(images[position]));
        return convertView;
    }


    static class ViewHolder {
        ImageView iv_icon;
    }

    public  int getImage(int position){
        return images[position];
    }
}
