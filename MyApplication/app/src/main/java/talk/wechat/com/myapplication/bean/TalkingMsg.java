package talk.wechat.com.myapplication.bean;

/**
 * Created by Administrator on 2016/11/17.
 */
public class TalkingMsg {
    private long id;
    private int from = -1;//0代表自己发出，1代表对方
    private int type = -1;//0代表文本，1代表语音,2代表表情
    private String content;//文本消息为实际内容，语音消息为url
    private int resId;//图片资源ID

    public final static int FROM_SELF = 0;
    public final static int FROM_OTHER = 1;
    public final static int TYPE_TEXT = 0;
    public final static int TYPE_VOICE = 1;
    public final static int TYPE_EMOJI = 2;

    public TalkingMsg() {

    }

    public TalkingMsg(int f, int t, String c) {
        from = f;
        type = t;
        content = c;
    }

    public TalkingMsg(int f, int t, int imageResId) {
        from = f;
        type = t;
        resId = imageResId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
