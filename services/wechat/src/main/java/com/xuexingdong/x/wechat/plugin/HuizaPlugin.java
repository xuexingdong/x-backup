// package com.xuexingdong.x.wechat.plugin;
//
// import com.xuexingdong.x.wechat.sdk.builder.TextBuilder;
// import com.xuexingdong.x.wechat.sdk.message.request.common.RecognitionVoiceMessage;
// import com.xuexingdong.x.wechat.sdk.message.response.TextResponse;
// import com.xuexingdong.x.wechat.sdk.message.response.WechatResponseMessage;
// import net.sourceforge.pinyin4j.PinyinHelper;
// import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
// import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
// import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
// import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
//
// import java.util.Optional;
//
// @Component
// public class HuizaPlugin implements WechatPlugin {
//
//     private static final Logger logger = LoggerFactory.getLogger(HuizaPlugin.class);
//
//     public static final String ANSWER = "hui4za2";
//
//     @Override
//     public Optional<? extends WechatResponseMessage> handleRecognitionVoice(RecognitionVoiceMessage recognitionVoiceMessage) {
//         HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//         format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
//         format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//         char[] chars = recognitionVoiceMessage.getRecognition().toCharArray();
//         StringBuilder sb = new StringBuilder();
//         for (char ch : chars) {
//             try {
//                 String[] res = PinyinHelper.toHanyuPinyinStringArray(ch, format);
//                 if (res == null || res.length == 0) {
//                     continue;
//                 }
//                 sb.append(res[0]);
//             } catch (BadHanyuPinyinOutputFormatCombination ignored) {
//             }
//         }
//         logger.info("语音识别结果: {},", sb.toString());
//         // wrong answer
//         if (!ANSWER.equals(sb.toString())) {
//             TextResponse textResponse = new TextBuilder()
//                     .from(recognitionVoiceMessage.getToUserName())
//                     .to(recognitionVoiceMessage.getFromUserName())
//                     .content("嗯...好像你不是我要找的那个人").build();
//             return Optional.of(textResponse);
//         }
//
//         // 嗯...貌似被你猜中了，但我还要确定下你是不是本人哦，请点击下面的链接
//
//         return Optional.of(textBuilder.content(content).build());
//     }
//
//     @Override
//     public int order() {
//         return 1;
//     }
// }
