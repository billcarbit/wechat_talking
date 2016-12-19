package talk.wechat.com.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import talk.wechat.com.myapplication.R;
import talk.wechat.com.myapplication.bean.TalkingMsg;
import talk.wechat.com.myapplication.media.Voice;

/**
 * Created by Administrator on 2016/11/17.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<TalkingMsg> mMsgList = new ArrayList<TalkingMsg>();

    public ListViewAdapter(Context context) {
        mContext = context;
    }

    public void addTalkingMsg(TalkingMsg tmg) {
        if (mMsgList != null) {
            mMsgList.add(tmg);
        }
    }

    public  List<TalkingMsg> getMsgList(){
        return mMsgList;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mMsgList.get(position).getFrom();
    }

    @Override
    public int getCount() {
        return mMsgList == null ? 0 : mMsgList.size();
    }

    @Override
    public TalkingMsg getItem(int position) {
        return mMsgList == null ? new TalkingMsg() : mMsgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mMsgList == null) {
            return -1;
        } else if (mMsgList.get(position) == null) {
            return -1;
        } else {
            return mMsgList.get(position).getId();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final TalkingMsg talkingMsg = getItem(position);
        if (convertView == null) {
            if (talkingMsg.getFrom() == TalkingMsg.FROM_SELF) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_right,parent, false);
            } else if (talkingMsg.getFrom() == TalkingMsg.FROM_OTHER) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_left, parent,false);
            }
            holder = new ViewHolder();
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (talkingMsg.getType() == TalkingMsg.TYPE_VOICE) {
            holder.tv_message.setText("((( 语音消息");
            holder.tv_message.setCompoundDrawables(null, null, null, null);
            holder.tv_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = talkingMsg.getContent();
                    Voice.play(url);
                }
            });
        } else if (talkingMsg.getType() == TalkingMsg.TYPE_TEXT) {
            holder.tv_message.setText(talkingMsg.getContent());
            holder.tv_message.setCompoundDrawables(null, null, null, null);
        } else if (talkingMsg.getType() == TalkingMsg.TYPE_EMOJI) {
            Drawable drawable = mContext.getResources().getDrawable(talkingMsg.getResId());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
            holder.tv_message.setCompoundDrawables(drawable, null, null, null);
            holder.tv_message.setText("");
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_avatar;
        TextView tv_message;
    }
}
