package com.robotChat;

/**
 * 聊天机器人
 * @author liudaxia
 *
 */
public class RobotChat {
	
	private static String  robotHomeUrl = "http://i.itpk.cn/api.php";
	private static String api_key = "c8efa73dd3fe6a1b68c123fc5f867786";
	private static String api_pass="hlqzygg3x2zz";
	public static String responseMessage(String question){
		StringBuilder sb = new StringBuilder();
		sb.append(robotHomeUrl);
		sb.append("?question=").append(question);
		sb.append("&api_key=").append(api_key);
		sb.append("&api_secret=").append(api_pass);
		String message = HttpClientUtil.doGet(sb.toString());
		
		return message;
	}

}
