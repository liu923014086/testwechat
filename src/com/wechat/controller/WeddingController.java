package com.wechat.controller;

import com.wechat.entity.po.Wedding;
import com.wechat.entity.po.WeddingImage;
import com.wechat.entity.po.WeddingMainInfo;
import com.wechat.entity.po.WeddingZanLog;
import com.wechat.service.wechat.WechatService;
import com.wechat.utils.CheckUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class WeddingController {

	private static Logger log = Logger.getLogger(WeddingController.class);
	@Autowired
	WechatService wechatService;



	/**
	 * 发送小程序需要的信息
	 *
	 * @param out
	 * @param request
	 * @param response
	 */
	private Map<String,String> faceMapContext = new HashMap<String, String>();
	private Map<String,Integer> zanMapContext = new HashMap<String, Integer>();
	@ResponseBody
	@RequestMapping(value = "/wedding", method = RequestMethod.GET)
	public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {

		Wedding w = new Wedding();
		////////////接收祝福
		String nickname = request.getParameter("nickname");
		String face = request.getParameter("face");//头像的路径（可保存在数据库，临时先用一个map存储）
		if(face!=null&&!"".equals(face)) {
			faceMapContext.put(nickname, face);
			Integer zanNum = zanMapContext.get("zanNum");
			if (zanNum == null) {
				zanMapContext.put("zanNum", 1);
			} else {
				zanMapContext.put("zanNum", zanMapContext.get("zanNum") + 1);
			}

			w.setMsg("点赞成功");
		}


		//////////////////////首页

		w.setMusic_url("music_url");
		w.getSlideList().add(new WeddingImage("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png"));
		w.getSlideList().add(new WeddingImage("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg"));
		w.getSlideList().add(new WeddingImage("http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg"));

		///////////////////邀请
		WeddingMainInfo mainInfo = new WeddingMainInfo();
		mainInfo.setHe("刘大侠");
		mainInfo.setShe("王小珍");
		mainInfo.setAddress("beijing");
		mainInfo.setDate("2018-05-01");
		mainInfo.setLunar("十六");
		mainInfo.setAddress("大辛");
		mainInfo.setHe_tel("18518366652");
		mainInfo.setShe_tel("13161632053");
		mainInfo.setLat(116.335853f);
		mainInfo.setLng(40.002578f);
		w.setMainInfo(mainInfo);

		//送上祝福
		if(faceMapContext!=null&&faceMapContext.size()>0) {
			Set<Map.Entry<String, String>> entries = faceMapContext.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				WeddingZanLog zanLog = new WeddingZanLog(entry.getValue());
				w.getZanLog().add(zanLog);
				w.setZanNum(zanMapContext.get("zanNum"));
			}
		}





		JSONObject jsonObject = JSONObject.fromObject(w);
		out.print(jsonObject);
		out.flush();
	}

}
