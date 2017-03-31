/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.robotChat;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wechat.utils.JsonUtil;
import com.wechat.utils.WechatMessageUtil;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class ClientMultipartFormPost {

    public static void main(String[] args) throws Exception {
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	String appid = "wxa9f27b15ba93cab7";
        	String secret = "17017122ed89f23428ac217569c39ae0";
        	String accessToken = WechatMessageUtil.getAccessToken(appid, secret);
        	System.out.println("accessToken="+accessToken);
            HttpPost httppost = new HttpPost("https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image");

            FileBody bin = new FileBody(new File("E:\\20170329113418.jpg"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("bin", bin)
                    .addPart("comment", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                
                if (resEntity != null) { 
                	InputStream content = resEntity.getContent(); 
                    byte[] bytes = new byte[content.available()];
                    content.read(bytes);
                    String responseMsg = new String(bytes);
                  //  System.err.println(responseMsg);
                    Map<String, Object> jsonToMap = JsonUtil.jsonToMap(responseMsg);
                    Integer errcode = (Integer) jsonToMap.get("errcode");
                    String errmsg = (String) jsonToMap.get("errmsg");
                    System.out.println("errcode="+errcode);
                    System.out.println("errmsg="+errmsg);
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
