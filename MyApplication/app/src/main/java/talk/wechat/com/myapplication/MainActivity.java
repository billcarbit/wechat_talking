package talk.wechat.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import talk.wechat.com.myapplication.adapter.EmojiGridViewAdapter;
import talk.wechat.com.myapplication.adapter.ListViewAdapter;
import talk.wechat.com.myapplication.bean.TalkingMsg;
import talk.wechat.com.myapplication.http.HttpVirtualServer;
import talk.wechat.com.myapplication.media.Voice;

public class MainActivity extends Activity {
    public final static String TAG = MainActivity.class.getSimpleName();
    private TextView tv_plus, tv_voice, tv_press_talk, tv_emoji;
    private EditText et_message;
    private ListView lv_content;
    private GridView gv_emoji;
    private ListViewAdapter mListViewAdapter;
    private EmojiGridViewAdapter mEmojiGridViewAdapter;

    private TextWatcher mEtMessageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }
            if (TextUtils.isEmpty(s.toString())) {
                onContentEmpty();
            } else {
                onContentFill(s.toString());
            }
        }
    };
    //表情发送
    private AdapterView.OnItemClickListener mGvEmojiOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (tv_emoji != null && gv_emoji != null && mEmojiGridViewAdapter != null && mListViewAdapter != null) {
                TalkingMsg talkingMsg = new TalkingMsg(TalkingMsg.FROM_SELF, TalkingMsg.TYPE_EMOJI, mEmojiGridViewAdapter.getImage(position));
                mListViewAdapter.addTalkingMsg(talkingMsg);
                mListViewAdapter.notifyDataSetChanged();
                gv_emoji.setVisibility(View.GONE);
                tv_emoji.setText("表情");
                //以下模拟网络响应
                httpResponse();
            }
        }
    };

    //按住 说话
    private View.OnTouchListener mTvPressTalkOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if(action == MotionEvent.ACTION_CANCEL){
                if (tv_press_talk != null) {
                    tv_press_talk.setText("按住 说话");
                }
                Voice.cancelRecording();
            }
            if (action == MotionEvent.ACTION_DOWN) {
                if (tv_press_talk != null) {
                    tv_press_talk.setText("松开 结束");
                }
                Voice.startRecording();
            }
            if (action == MotionEvent.ACTION_UP) {
                if (tv_press_talk != null) {
                    tv_press_talk.setText("按住 说话");
                }
                Voice.stopRecording();
                TalkingMsg talkingMsg = new TalkingMsg(TalkingMsg.FROM_SELF, TalkingMsg.TYPE_VOICE, Voice.filePath);
                if (mListViewAdapter != null) {
                    mListViewAdapter.addTalkingMsg(talkingMsg);
                    mListViewAdapter.notifyDataSetChanged();
                    //以下模拟网络响应
                    httpResponse();
                }
            }
            return true;
        }
    };

    //表情
    private View.OnClickListener mTvEmojiOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (gv_emoji != null && tv_emoji != null && TextUtils.equals(tv_emoji.getText(), "表情")) {
                tv_emoji.setText("键盘");
                gv_emoji.setVisibility(View.VISIBLE);
            } else if (gv_emoji != null && tv_emoji != null && TextUtils.equals(tv_emoji.getText(), "键盘")) {
                tv_emoji.setText("表情");
                gv_emoji.setVisibility(View.GONE);
            }
        }
    };

    //语音
    private View.OnClickListener mTvVoiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tv_voice == null) {
                return;
            }
            if (TextUtils.equals(tv_voice.getText(), "语音")) {
                tv_voice.setText("文字");
                et_message.setVisibility(View.GONE);
                tv_press_talk.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(tv_voice.getText(), "文字")) {
                tv_voice.setText("语音");
                et_message.setVisibility(View.VISIBLE);
                tv_press_talk.setVisibility(View.GONE);

            }
        }
    };

    //发送
    private View.OnClickListener mTvPlusOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tv_plus == null) {
                return;
            }
            if (lv_content != null && gv_emoji != null && mListViewAdapter != null && et_message != null && TextUtils.equals(tv_plus.getText(), "发送")) {
                CharSequence etMsg = et_message.getText();
                TalkingMsg talkingMsg = new TalkingMsg(TalkingMsg.FROM_SELF, TalkingMsg.TYPE_TEXT, etMsg.toString());
                mListViewAdapter.addTalkingMsg(talkingMsg);
                mListViewAdapter.notifyDataSetChanged();
                et_message.setText(null);
                gv_emoji.setVisibility(View.GONE);
                hideSoftInputMode(MainActivity.this, et_message);
                lv_content.setSelection(mListViewAdapter.getCount() - 1);
                //以下模拟服务器响应
                httpResponse();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        tv_plus = (TextView) findViewById(R.id.tv_plus);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        tv_emoji = (TextView) findViewById(R.id.tv_emoji);
        tv_press_talk = (TextView) findViewById(R.id.tv_press_talk);
        et_message = (EditText) findViewById(R.id.et_message);
        lv_content = (ListView) findViewById(R.id.lv_content);
        gv_emoji = (GridView) findViewById(R.id.gv_emoji);

    }

    private void initListener() {
        tv_plus.setOnClickListener(mTvPlusOnClickListener);
        tv_voice.setOnClickListener(mTvVoiceOnClickListener);
        tv_emoji.setOnClickListener(mTvEmojiOnClickListener);
        et_message.addTextChangedListener(mEtMessageTextWatcher);
        tv_press_talk.setOnTouchListener(mTvPressTalkOnTouchListener);
        gv_emoji.setOnItemClickListener(mGvEmojiOnItemClickListener);

    }

    private void initAdapter() {
        mListViewAdapter = new ListViewAdapter(this);
        lv_content.setAdapter(mListViewAdapter);

        mEmojiGridViewAdapter = new EmojiGridViewAdapter(this);
        gv_emoji.setAdapter(mEmojiGridViewAdapter);
    }

    private void onContentFill(String content) {
        if (tv_plus != null && !TextUtils.equals(tv_plus.getText(), "发送")) {
            tv_plus.setText("发送");
        }
    }

    private void onContentEmpty() {
        if (tv_plus != null && !TextUtils.equals(tv_plus.getText(), "+")) {
            tv_plus.setText("+");
        }
    }

    private void httpResponse() {
        TalkingMsg talkingMsgFromServer = HttpVirtualServer.response();
        mListViewAdapter.addTalkingMsg(talkingMsgFromServer);
        mListViewAdapter.notifyDataSetChanged();
    }


    /**
     * 隐藏软键盘
     */

    public static void hideSoftInputMode(Context context, View windowToken) {
        InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
