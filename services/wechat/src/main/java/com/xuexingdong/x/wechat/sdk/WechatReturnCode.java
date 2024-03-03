package com.xxd.x.wechat.sdk;

public enum WechatReturnCode {

    SYSTEM_BUSY(-1, "系统繁忙，此时请开发者稍候再试"),

    SUCCESS(0, "请求成功"),

    INVALID_CREDENTIAL(40001, "获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口"),
    ILLEGAL_CERTIFICATE_TYPE(40002, "不合法的凭证类型"),
    ILLEGAL_OPENID(40003, "不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID"),
    ILLEGAL_MEDIA_FILE_TYPE(40004, "不合法的媒体文件类型"),
    ILLEGAL_FILE_TYPE(40005, "不合法的文件类型"),
    ILLEGAL_FILE_SIZE(40006, "不合法的文件大小"),
    ILLEGAL_MEDIA_FILE_ID(40008, "不合法的媒体文件id"),
    ILLEGAL_MSG_TYPE(40008, "不合法的消息类型"),
    ILLEGAL_IMAGE_FILE_SIZE(40009, "不合法的图片文件大小"),
    ILLEGAL_VOICE_FILE_SIZE(40010, "不合法的语音文件大小"),
    ILLEGAL_VIDEO_FILE_SIZE(40011, "不合法的视频文件大小"),
    ILLEGAL_APPID(40013, "不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写"),
    ILLEGAL_ACCESS_TOKEN(40014, "不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口"),
    ILLEGAL_MENU_TYPE(40015, "不合法的菜单类型"),
    ILLEGAL_BUTTON_NUMBER(40016, "不合法的按钮个数"),
    ILLEGAL_BUTTON_NUMBER_ALIAS(40017, "不合法的按钮个数"),
    ILLEGAL_BUTTON_NAME_LENGTH(40018, "不合法的按钮名字长度"),
    ILLEGAL_BUTTON_KEY_LENGTH(40019, "不合法的按钮KEY长度"),
    ILLEGAL_SUB_MENU_BUTTON_NUMBER(40023, "不合法的子菜单按钮个数"),
    ILLEGAL_SUB_MENU_BUTTON_TYPE(40024, "不合法的子菜单按钮类型"),
    ILLEGAL_SUB_MENU_BUTTON_NAME_LENGTH(40025, "不合法的子菜单按钮名字长度"),
    ILLEGAL_SUB_MENU_BUTTON_KEY_LENGTH(40026, "不合法的子菜单按钮KEY长度"),
    ILLEGAL_SUB_MENU_BUTTON_URL_LENGTH(40027, "不合法的子菜单按钮URL长度"),
    ILLEGAL_CUSTOM_MENU_USER(40028, "不合法的自定义菜单使用用户"),
    ILLEGAL_OAUTH_CODE(40029, "不合法的oauth_code"),
    ILLEGAL_REFRESH_TOKEN(40030, "不合法的refresh_token"),
    ILLEGAL_OPENID_LIST(40031, "不合法的openid列表"),
    ILLEGAL_OPENID_LIST_LENGTH(40032, "不合法的openid列表长度"),
    ILLEGAL_REQUEST_CHARACTER(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符"),
    ILLEGAL_PARAMATER(40035, "不合法的参数"),
    ILLEGAL_REQUEST_FORMAT(40038, "不合法的请求格式"),
    ILLEGAL_URL_LENGTH(40039, "不合法的URL长度"),
    ILLEGAL_GROUP_ID(40050, "不合法的分组id"),
    ILLEGAL_GROUP_NAME(40051, "分组名字不合法"),
    ILLEGAL_ARTICLE_IDX(40060, "删除单篇图文时，指定的 article_idx 不合法"),
    ILLEGAL_GROUP_NAME_ALIAS(40117, "分组名字不合法"),
    ILLEGAL_MEDIA_ID_SIZE(40118, "media_id大小不合法"),
    BUTTON_TYPE_ERROR(40119, "button类型错误"),
    BUTTON_TYPE_ERROR_ALIAS(40120, "button类型错误"),
    ILLEGAL_MEDIA_ID_TYPE(40121, "不合法的media_id类型"),
    ILLEGAL_WECHAT_ID(40132, "微信号不合法"),
    UNSUPPORTED_IMAGE_FORMAT(40137, "不支持的图片格式"),
    // NOTE any better description?
    NO_ADD_OTHER_MEDIA_PLATFORM_HOMEPAGE_LINK(40155, "请勿添加其他公众号的主页链接"),

    MISSING_ACCESS_TOKEN_PARAMATER(41001, "缺少access_token参数"),
    MISSING_APP_ID_PARAMATER(41002, "缺少appid参数"),
    MISSING_REFRESH_TOKEN_PARAMATER(41003, "缺少refresh_token参数"),
    MISSING_SECRET_PARAMATER(41004, "缺少secret参数"),
    MISSING_MEDIA_FILE_DATA(41005, "缺少多媒体文件数据"),
    MISSING_MEDIA_ID_PARAMATER(41006, "缺少media_id参数"),
    MISSING_SUB_MENU_DATA(41007, "缺少子菜单数据"),
    MISSING_OAUTH_CODE(41008, "缺少oauth code"),
    MISSING_OPENID(41009, "缺少openid"),

    TIMEOUT_ACCESS_TOKEN(42001, "access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明"),
    TIMEOUT_REFRESH_TOKEN(42002, "refresh_token超时"),
    TIMEOUT_OAUTH_CODE(42003, "oauth_code超时"),
    USER_CHANGE_PASSWORD(42003, "用户修改微信密码，accesstoken和refreshtoken失效，需要重新授权"),

    NEEDS_GET_REQUEST(43001, "需要GET请求"),
    NEEDS_POST_REQUEST(43002, "需要POST请求"),
    NEEDS_HTTPS_REQUEST(43003, "需要HTTPS请求"),
    NEEDS_RECIPIENT_REQUEST(43004, "需要接收者关注"),
    NEEDS_FRIEND_RELATIONS(43005, "需要好友关系"),
    NEEDS_REMOVE_RECEIVER_FROM_BLACKLIST(43019, "需要将接收者从黑名单中移除"),

    EMPTY_MEDIA_FILE(44001, "多媒体文件为空"),
    EMPTY_POST_PACKET(44002, "POST的数据包为空"),
    EMPTY_IMAGE_INFORMATION_CONTENT(44003, "图文消息内容为空"),
    EMPTY_TEXT_CONTENT(44004, "文本消息内容为空"),

    MEDIA_FILE_SIZE_OVER_LIMIT(45001, "多媒体文件大小超过限制"),
    MESSAGE_CONTENT_OVER_LIMIT(45002, "消息内容超过限制"),
    TITLE_OVER_LIMIT(45003, "标题字段超过限制"),
    DESCRIPTION_OVER_LIMIT(45004, "描述字段超过限制"),
    LINK_OVER_LIMIT(45005, "链接字段超过限制"),
    IMAGE_LINK_OVER_LIMIT(45006, "图片链接字段超过限制"),
    VOICE_TIME_OVER_LIMIT(45007, "语音播放时间超过限制"),
    NEWS_MESSAGE_OVER_LIMIT(45008, "图文消息超过限制"),
    INTERFACE_CALL_TIME_OVER_LIMIT(45009, "接口调用超过限制"),
    MENU_NUMBER_OVER_LIMIT(45010, "创建菜单个数超过限制"),
    API_CALL_TIME_OVER_LIMIT(45011, "API调用太频繁，请稍候再试"),
    RESPONSE_TIME_OVER_LIMIT(45015, "回复时间超过限制"),
    CAN_NOT_MODIFY_SYSTEM_GROUP(45016, "系统分组，不允许修改"),
    GROUP_NAME_LENGTH_OVER_LIMIT(45017, "分组名字过长"),
    GROUP_NUMBER_OVER_LIMIT(45018, "分组数量超过上限"),
    KF_DOWN_MESSAGE_NUMBER_OVER_LIMIT(45018, "客服接口下行条数超过上限"),

    NOT_EXISTS_MEIDA_DATA(46001, "不存在媒体数据"),
    NOT_EXISTS_MENU_VERSION(46002, "不存在的菜单版本"),
    NOT_EXISTS_MENU_DATA(46003, "不存在的菜单数据"),
    NOT_EXISTS_USER(46004, "不存在的用户"),

    ERROR_PARSING_JSON_OR_XML(47001, "解析JSON/XML内容错误"),

    API_UNAUTHORIZED(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"),
    USER_REJECTED_MESSAGE(48002, "粉丝拒收消息（粉丝在公众号选项中，关闭了“接收消息”）"),
    API_IS_BLOCKED(48004, "api接口被封禁，请登录mp.weixin.qq.com查看详情"),
    API_CANNOT_DELETE_MATERIAL_BEING_USED(48005, "api禁止删除被自动回复和自定义菜单引用的素材"),
    API_ZERO_TIMES_OVER_LIMIT(48006, "api禁止清零调用次数，因为清零次数达到上限"),

    API_USER_UNAUTHORIZED(50001, "用户未授权该api"),

    INVALID_PARAMETER(61451, "参数错误(invalid parameter)"),
    INVALID_KF_ACCOUNT(61452, "无效客服账号(invalid kf_account)"),
    KF_ACCOUNT_EXSITED(61453, "客服帐号已存在(kf_account exsited)"),
    INVALID_KF_ACCOUNT_LENGTH(61454, "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)"),
    ILLEGAL_CHARACTER_IN_KF_ACCOUNT(61455, "客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)"),
    KF_ACCOUNT_COUNT_EXCEEDED(61456, "客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)"),
    INVALID_FILE_TYPE(61457, "无效头像文件类型(invalid file type)"),
    SYSTEM_ERROR(61450, "系统错误(system error)"),
    ERROR_DATE_FORMAT(61500, "日期格式错误"),

    NOT_EXISTES_PERSONALIZED_MENU(65301, "不存在此menuid对应的个性化菜单"),
    NOT_EXISTES_SUCH_USER(65302, "没有相应的用户"),
    CREATE_PERSONALIZED_MENU_WITHOUT_DEFAULT_MENU(65303, "没有默认菜单，不能创建个性化菜单"),
    MATCH_RULE_IS_EMPTY(65304, "MatchRule信息为空"),
    PERSONALIZED_MENU_IS_LIMITED(65305, "个性化菜单数量受限"),
    UNSUPPORTED_PERSONALIZED_MENU(65306, "不支持个性化菜单的帐号"),
    PERSONALIZED_MENU_IS_EMPTY(65307, "个性化菜单信息为空"),
    BUTTON_WITHOUT_RESPONSE_TYPE(65308, "包含没有响应类型的button"),
    PERSONALIZED_MENU_IS_CLOSED(65309, "个性化菜单开关处于关闭状态"),
    COUNTRY_INFO_IS_EMPTY(65310, "填写了省份或城市信息，国家信息不能为空"),
    PROVINCE_INFO_IS_EMPTY(65311, "填写了城市信息，省份信息不能为空"),
    ILLEGAL_COUNTRY_INFO(65312, "不合法的国家信息"),
    ILLEGAL_PROVINCE_INFO(65313, "不合法的省份信息"),
    ILLEGAL_CITY_INFO(65314, "不合法的城市信息"),
    TOO_MANY_OUTER_DOMAIN(65316, "该公众号的菜单设置了过多的域名外跳（最多跳转到3个域名的链接）"),
    ILLEGAL_URL(65317, "不合法的URL"),

    ILLEGAL_POST_DATA_PARAMETERS(9001001, "POST数据参数不合法"),
    UNAVAILABLE_REMOTE_SERVICE(9001002, "远端服务不可用"),
    INVALID_TICKET(9001003, "Ticket不合法"),
    FAILED_TO_GET_USERINFO_OF_PERIPHERY(9001004, "获取摇周边用户信息失败"),
    FAILED_TO_GET_BUSINESS_INFO(9001005, "获取商户信息失败"),
    FAILED_TO_GET_OPEN_ID(9001006, "获取OpenID失败"),
    MISSING_UPLOAD_FILE(9001007, "上传文件缺失"),
    ILLEGAL_FILE_TYPE_OF_UPLOAD_MATERIAL(9001008, "上传素材的文件类型不合法"),
    ILLEGAL_FILE_SIZE_OF_UPLOAD_MATERIAL(9001009, "上传素材的文件尺寸不合法"),
    FAILED_TO_UPLOAD(9001010, "上传失败"),
    ILLEGAL_ACCOUNT(9001020, "帐号不合法"),
    CANNOT_ADD_NEW_DEVICE(9001021, "已有设备激活率低于50%，不能新增设备"),
    ILLEGAL_DEVICE_APPLY_NUMBER(9001022, "设备申请数不合法，必须为大于0的数字"),
    DEVICE_ID_ALREADY_EXISTS(9001023, "已存在审核中的设备ID申请"),
    DEVICE_ID_NUMBERS_FOR_ONE_QUERY_MUST_LESS_THAN_FIFTY(9001024, "一次查询设备ID数量不能超过50"),
    ILLEGAL_DEVICE_ID(9001025, "设备ID不合法"),
    ILLEGAL_PAGE_ID(9001026, "页面ID不合法"),
    ILLEGAL_PAGE_PARAMETERS(9001027, "页面参数不合法"),
    REMOVE_PAGE_ID_NUMBERS_MUST_LESS_THAN_TEN(9001028, "一次删除页面ID数量不能超过10"),
    CANNOT_REMOVE_PAGE_BEING_USED_IN_DEVICE(9001029, "页面已应用在设备中,请先解除应用关系再删除"),
    PAGE_ID_NUMBERS_FOR_ONE_QUERY_MUST_LESS_THAN_FIFTY(9001030, "一次查询页面ID数量不能超过50"),
    ILLEGAL_TIME_INTERVAL(9001031, "时间区间不合法"),
    PARAMETER_ERROR_OF_BINDING_RELATION_BETWEEN_DEVICE_AND_PAGE(9001032, "保存设备与页面的绑定关系参数错误"),
    ILLEGAL_STORE_ID(9001033, "门店ID不合法"),
    ILLEGAL_DEVICE_REMARK_LENGTH(9001034, "设备备注信息过长"),
    ILLEGAL_DEVICE_APPLY_PARAMETER(9001035, "设备申请参数不合法"),
    ILLEGAL_QUERY_BEGIN_VALUE(9001036, "查询起始值begin不合法");

    private int code;
    private String msg;

    WechatReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
