package com.xxd.x.wechat.plugin;

import com.xxd.x.common.serialization.XSerialization;
import com.xxd.x.wechat.sdk.constant.Event;
import com.xxd.x.wechat.sdk.constant.MsgType;
import com.xxd.x.wechat.sdk.message.request.common.*;
import com.xxd.x.wechat.sdk.message.request.event.SubscribeEvent;
import com.xxd.x.wechat.sdk.message.response.WechatResponseMessage;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Objects;
import java.util.Optional;

public interface WechatPlugin {

    default int order() {
        return 0;
    }

    default Optional<? extends WechatResponseMessage> handle(Document document) {
        String xml = document.asXML();
        Element root = document.getRootElement();
        String msgTypeStr = String.valueOf(root.element("MsgType").getData());
        MsgType msgType = MsgType.valueOf(msgTypeStr.toUpperCase());
        switch (msgType) {
            case TEXT:
                TextMessage textMessage = XSerialization.fromXML(xml, TextMessage.class);
                return handleText(textMessage);
            case IMAGE:
                ImageMessage imageMessage = XSerialization.fromXML(xml, ImageMessage.class);
                return handleImage(imageMessage);
            case VOICE:
                if (Objects.isNull(root.element("Recognition"))) {
                    VoiceMessage voiceMessage = XSerialization.fromXML(xml, VoiceMessage.class);
                    return handleVoice(voiceMessage);
                } else {
                    RecognitionVoiceMessage recognitionVoiceMessage = XSerialization.fromXML(xml, RecognitionVoiceMessage.class);
                    return handleRecognitionVoice(recognitionVoiceMessage);
                }
            case VIDEO:
                VideoMessage videoMessage = XSerialization.fromXML(xml, VideoMessage.class);
                return handleVideo(videoMessage);
            case SHORTVIDEO:
                ShortVideoMessage shortVideoMessage = XSerialization.fromXML(xml, ShortVideoMessage.class);
                return handleShortVideo(shortVideoMessage);
            case LOCATION:
                LocationMessage locationMessage = XSerialization.fromXML(xml, LocationMessage.class);
                return handleLocation(locationMessage);
            case LINK:
                LinkMessage linkMessage = XSerialization.fromXML(xml, LinkMessage.class);
                return handleLink(linkMessage);
            case EVENT:
                String eventStr = String.valueOf(root.element("Event").getData());
                Event eventType = Event.valueOf(eventStr.toUpperCase());
                switch (eventType) {
                    case SUBSCRIBE:
                        SubscribeEvent subscribeEvent = XSerialization.fromXML(xml, SubscribeEvent.class);
                        return handleSubscribe(subscribeEvent);
                    case UNSUBSCRIBE:
                        break;
                    case SCAN:
                        break;
                    case LOCATION:
                        break;
                    case CLICK:
                        break;
                    case VIEW:
                        break;
                    default:
                        break;
                }
                break;
        }
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleLink(LinkMessage linkMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleLocation(LocationMessage locationMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleShortVideo(ShortVideoMessage shortVideoMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleSubscribe(SubscribeEvent event) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleText(TextMessage textMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleImage(ImageMessage imageMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleVideo(VideoMessage videoMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleVoice(VoiceMessage voiceMessage) {
        return Optional.empty();
    }

    default Optional<? extends WechatResponseMessage> handleRecognitionVoice(RecognitionVoiceMessage recognitionVoiceMessage) {
        return Optional.empty();
    }
}
