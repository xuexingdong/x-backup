package com.xxd.x.chatbot.webwx;

import java.util.HashSet;
import java.util.Set;

public class Emotion {

    private static final Set<String> EMOTION_SET = new HashSet<String>() {{
        add("微笑");
        add("撇嘴");
        add("色");
        add("发呆");
        add("得意");
        add("流泪");
        add("害羞");
        add("闭嘴");
        add("睡");
        add("大哭");
        add("尴尬");
        add("发怒");
        add("调皮");
        add("龇牙");
        add("惊讶");
        add("难过");
        add("囧");
        add("抓狂");
        add("吐");
        add("偷笑");
        add("愉快");
        add("白眼");
        add("傲慢");

        add("困");
        add("惊恐");
        add("流汗");
        add("憨笑");
        add("悠闲");
        add("奋斗");
        add("咒骂");
        add("疑问");
        add("嘘");
        add("晕");
        add("衰");
        add("骷髅");
        add("敲打");
        add("再见");
        add("擦汗");
        add("抠鼻");
        add("鼓掌");
        add("坏笑");
        add("左哼哼");
        add("右哼哼");
        add("哈欠");
        add("鄙视");
        add("委屈");

        add("快哭了");
        add("阴险");
        add("亲亲");
        add("可怜");
        add("菜刀");
        add("西瓜");
        add("啤酒");
        add("咖啡");
        add("猪头");
        add("玫瑰");
        add("凋谢");
        add("嘴唇");
        add("爱心");
        add("心碎");
        add("蛋糕");
        add("炸弹");
        add("便便");
        add("月亮");
        add("太阳");
        add("拥抱");
        add("强");
        add("弱");
        add("握手");

        add("胜利");
        add("抱拳");
        add("勾引");
        add("拳头");
        add("OK");
        add("跳跳");
        add("发抖");
        add("怄火");
        add("转圈");

        add("嘿哈");
        add("捂脸");
        add("奸笑");
        add("机智");
        add("皱眉");
        add("耶");

        add("红包");
        add("發");
        add("小狗");
    }};

    public static boolean exists(String code) {
        return EMOTION_SET.contains(code);
    }
}
