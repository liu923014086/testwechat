package com.wechat.service.wechat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.photeDetact.FaceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.robotChat.RobotChat;
import com.wechat.entity.vo.wechat.message.TextMessage;
import com.wechat.utils.WechatMessageUtil;

@Service
public class WechatService {
	private static Logger log = Logger.getLogger(WechatService.class);

	public String processRequest(HttpServletRequest request) {
		Map<String, String> map = WechatMessageUtil.xmlToMap(request);
		System.out.println("map="+map.toString());
		//{MsgId=6403174220112945990, FromUserName=olIovw1XNEdN9tgHrLcw2fiOQAyA, CreateTime=1490855175,
		//Content=什么, ToUserName=gh_7b81759f7d29, MsgType=text}
		/**
		 * {MsgId=6403462068821137242, FromUserName=olIovw1XNEdN9tgHrLcw2fiOQAyA, CreateTime=1490922195, 
		 * Content=你好, ToUserName=gh_7b81759f7d29, MsgType=text}
		 */
		
		/**
		 * 从这里引入机器人
		 */
		String question = map.get("Content");
		String responseMessage2 = RobotChat.responseMessage(question);
		
		log.info(map);
		// 发送方帐号（一个OpenID）
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");
		// 默认回复一个"success"
		String responseMessage = "success";
		// 对消息进行处理
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(System.currentTimeMillis());
		textMessage.setMsgType(WechatMessageUtil.MESSAGE_TEXT);
		if (WechatMessageUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
			textMessage.setContent(responseMessage2);

		}else if(WechatMessageUtil.MESSAtGE_IMAGE.equalsIgnoreCase(msgType)){
			// 取得图片地址
			String picUrl = map.get("PicUrl");
			// 人脸检测
			String detectResult = FaceService.detect(picUrl);
			textMessage.setContent(detectResult);
		}

		responseMessage = WechatMessageUtil.textMessageToXml(textMessage);
		log.info(responseMessage);
		return responseMessage;

	}

}
