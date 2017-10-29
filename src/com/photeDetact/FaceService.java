package com.photeDetact;

import java.io.BufferedReader;
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
/** 
 * 人脸检测服务 
 *  
 * @author liufeng 
 * @date 2013-12-18 
 */  
public class FaceService {  
    /** 
     * 发送http请求 
     *  
     * @param requestUrl 请求地址 
     * @return String 
     */  
    private static String httpRequest(String requestUrl,String post) {
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            /*httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            GET请求*/

            httpUrlConn.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpUrlConn.getOutputStream());
            // 发送请求参数
            printWriter.write(post);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();


            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer.toString();  
    }  
  
    /** 
     * 调用Face++ API实现人脸检测 
     *  
     * @param picUrl 待检测图片的访问地址 
     * @return List<Face> 人脸列表 
     */  
    private static List<Face> faceDetect(String picUrl) {  
        List<Face> faceList = new ArrayList<Face>();  
        try {  
            // 拼接Face++人脸检测的请求地址  
            String queryUrl = "https://api-cn.faceplusplus.com/facepp/v3/detect";
            // 对URL进行编码
            String postParam = "image_url=URL&api_secret=API_SECRET&api_key=API_KEY&return_attributes=RETURNATTRIBUTE";
            postParam = postParam.replace("URL", picUrl);
            postParam = postParam.replace("API_KEY", "VwhUaNIMYnzGuSFJDqexF2gVzIXhYp1F");
            postParam = postParam.replace("API_SECRET", "bVIZtk_Ap0lqZFA-12sjOkpCIV2RFiyR");
            postParam = postParam.replace("RETURNATTRIBUTE","gender,age,smiling,eyestatus,emotion,ethnicity,beauty");
            System.out.println("postParam = [" + postParam + "]");
            // 调用人脸检测接口  
            String json = httpRequest(queryUrl,postParam);
            System.out.println("json = [" + json + "]");
            // 解析返回json中的Face列表  
            JSONArray jsonArray = JSONObject.fromObject(json).getJSONArray("faces");
            // 遍历检测到的人脸  
            for (int i = 0; i < jsonArray.size(); i++) {  
                // face  
                JSONObject faceObject = (JSONObject) jsonArray.get(i);  
                // attribute  
                JSONObject attrObject = faceObject.getJSONObject("attributes");
                // position  
              //  JSONObject posObject = faceObject.getJSONObject("position");
                Face face = new Face();  
                face.setFaceId(faceObject.getString("face_token"));
                face.setAgeValue(attrObject.getJSONObject("age").getInt("value"));  
              //  face.setAgeRange(attrObject.getJSONObject("age").getInt("range"));
                face.setGenderValue(genderConvert(attrObject.getJSONObject("gender").getString("value")));  
              //  face.setGenderConfidence(attrObject.getJSONObject("gender").getDouble("confidence"));
                face.setRaceValue(raceConvert(attrObject.getJSONObject("ethnicity").getString("value")));
                //face.setRaceConfidence(attrObject.getJSONObject("race").getDouble("confidence"));
              //  face.setSmilingValue(attrObject.getJSONObject("smiling").getDouble("value"));
                JSONObject emotion = attrObject.getJSONObject("emotion");
                face.setEmotion(smilingConvert(emotion));
                face.setGlasses(eyeConvert(attrObject.getJSONObject("eyestatus").getJSONObject("left_eye_status").getDouble("no_glass_eye_open")));
                //  face.setCenterX(posObject.getJSONObject("center").getDouble("x"));
              //  face.setCenterY(posObject.getJSONObject("center").getDouble("y"));
                faceList.add(face);  
            }  
            // 将检测出的Face按从左至右的顺序排序  
            Collections.sort(faceList);  
        } catch (Exception e) {  
            faceList = null;  
            e.printStackTrace();  
        }  
        return faceList;  
    }  
  
    /** 
     * 性别转换（英文->中文） 
     *  
     * @param gender 
     * @return 
     */  
    private static String genderConvert(String gender) {  
        String result = "男性";  
        if ("Male".equals(gender))  
            result = "男性";  
        else if ("Female".equals(gender))  
            result = "女性";  
  
        return result;  
    }  
  
    /** 
     * 人种转换（英文->中文） 
     *  
     * @param race 
     * @return 
     */  
    private static String raceConvert(String race) {  
        String result = "黄色";  
        if ("Asian".equals(race))  
            result = "黄色";  
        else if ("White".equals(race))  
            result = "白色";  
        else if ("Black".equals(race))  
            result = "黑色";  
        return result;  
    }

    public static String eyeConvert(double eye){
        String glass = "戴眼镜";
        if(eye>=90){
            glass = "不戴眼镜";
        }

        return glass;
    }

    /**
     * anger：愤怒
     disgust：厌恶
     fear：恐惧
     happiness：高兴
     neutral：平静
     sadness：伤心
     surprise：惊讶
     */

    private static String smilingConvert(JSONObject emotion) {

        double anger = emotion.getDouble("anger");
        double disgust = emotion.getDouble("disgust");
        double fear = emotion.getDouble("fear");
        double happiness = emotion.getDouble("happiness");
        double neutral = emotion.getDouble("neutral");
        double sadness = emotion.getDouble("sadness");
        double surprise = emotion.getDouble("surprise");
        Map<Double,String> map = new HashMap<Double, String>();//value为汉字
        map.put(anger,"愤怒");
        map.put(disgust,"厌恶");
        map.put(fear,"恐惧");
        map.put(happiness,"高兴");
        map.put(neutral,"平静");
        map.put(sadness,"伤心");
        map.put(surprise,"惊讶");
        List<Double> list = new ArrayList<Double>();
        list.add(anger);
        list.add(disgust);
        list.add(fear);
        list.add(happiness);
        list.add(neutral);
        list.add(sadness);
        list.add(surprise);

        Collections.sort(list);
        Collections.reverse(list);

       // String result = "无法获取表情信息";

        return map.get(list.get(0));

    }

    /** 
     * 根据人脸识别结果组装消息 
     *  
     * @param faceList 人脸列表 
     * @return 
     */  
    private static String makeMessage(List<Face> faceList) {  
        StringBuffer buffer = new StringBuffer();  
        // 检测到1张脸  
        if (1 == faceList.size()) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("人种,");  
                buffer.append(face.getGenderValue()).append(",");  
                buffer.append(face.getAgeValue()).append("岁左右").append(","+face.getGlasses()).append(",您今天的心情是："+face.getEmotion()).append("\n");
            }  
        }  
        // 检测到2-10张脸  
        else if (faceList.size() > 1 && faceList.size() <= 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸，按脸部中心位置从左至右依次为：").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("人种,");  
                buffer.append(face.getGenderValue()).append(",");  
                buffer.append(face.getAgeValue()).append("岁左右").append(","+face.getGlasses()).append(",您今天的心情是："+face.getEmotion()).append("\n");
            }  
        }  
        // 检测到10张脸以上  
        else if (faceList.size() > 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
            // 统计各人种、性别的人数  
            int asiaMale = 0;  
            int asiaFemale = 0;  
            int whiteMale = 0;  
            int whiteFemale = 0;  
            int blackMale = 0;  
            int blackFemale = 0;  
            for (Face face : faceList) {  
                if ("黄色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        asiaMale++;  
                    else  
                        asiaFemale++;  
                else if ("白色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        whiteMale++;  
                    else  
                        whiteFemale++;  
                else if ("黑色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        blackMale++;  
                    else  
                        blackFemale++;  
            }  
            if (0 != asiaMale || 0 != asiaFemale)  
                buffer.append("黄色人种：").append(asiaMale).append("男").append(asiaFemale).append("女").append("\n");  
            if (0 != whiteMale || 0 != whiteFemale)  
                buffer.append("白色人种：").append(whiteMale).append("男").append(whiteFemale).append("女").append("\n");  
            if (0 != blackMale || 0 != blackFemale)  
                buffer.append("黑色人种：").append(blackMale).append("男").append(blackFemale).append("女").append("\n");  
        }  
        // 移除末尾空格  
        buffer = new StringBuffer(buffer.substring(0, buffer.lastIndexOf("\n")));  
        return buffer.toString();  
    }  
  
    /** 
     * 提供给外部调用的人脸检测方法 
     *  
     * @param picUrl 待检测图片的访问地址 
     * @return String 
     */  
    public static String detect(String picUrl) {  
        // 默认回复信息  
        String result = "未识别到人脸，请换一张清晰的照片再试！";  
        List<Face> faceList = faceDetect(picUrl);  
        if (null != faceList) {  
            result = makeMessage(faceList);  
        }  
        return result;  
    }  
  
    public static void main(String[] args) {  
        String picUrl = "https://pic4.zhimg.com/50/f32f2c534a8286b6594d878717b44af3_hd.jpg";
        System.out.println(detect(picUrl));  
    }  
}  