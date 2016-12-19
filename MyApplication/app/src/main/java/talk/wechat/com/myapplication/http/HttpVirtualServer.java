package talk.wechat.com.myapplication.http;

import java.util.Random;

import talk.wechat.com.myapplication.bean.TalkingMsg;

/**
 * Created by Administrator on 2016/11/17.
 */
public class HttpVirtualServer {
    private static HttpVirtualServer instance;

    private HttpVirtualServer() {
    }

    static HttpVirtualServer getInstance() {
        if (instance == null)
            instance = new HttpVirtualServer();
        return instance;
    }

    public static TalkingMsg response() {

        TalkingMsg talkingMsg = new TalkingMsg();
        talkingMsg.setContent("好的");
        talkingMsg.setFrom(TalkingMsg.FROM_OTHER);
        talkingMsg.setType(TalkingMsg.TYPE_TEXT);
        return talkingMsg;
    }

}
