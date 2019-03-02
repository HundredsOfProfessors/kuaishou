package com.kuaishou.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuaishou.demo.async.AsyncMethod;
import com.kuaishou.demo.pojo.Video;
import com.kuaishou.demo.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author chy
 * @date 2019-02-23
 */
@Slf4j
@RestController
public class DemoController {

    @Autowired
    private AsyncMethod asyncMethod;
    @Autowired
    private VideoService videoService;

    @GetMapping("demo1")
    public JSONArray   demo1() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.gifshow.com/rest/n/feed/hot?app=0&kpf=ANDROID_PHONE&ver=6.1&c=XIAOMI&mod=Xiaomi%28MIX%202%29&appver=6.1.1.8096&ftt=K-T-T&isp=CUCC&kpn=KUAISHOU&lon=121.307828&language=zh-cn&sys=ANDROID_8.0.0&max_memory=256&ud=1211439612&country_code=cn&oc=XIAOMI&hotfix_ver=&did_gt=1547968116027&iuid=&extId=c37f259c4d56246ff655c4627550fa26&net=WIFI&did=ANDROID_75eb73714a8769c6&lat=31.219055&type=7&page=1&coldStart=false&count=20&pv=false&id=14363&refreshTimes=2&pcursor=&source=1&needInterestTag=false&browseType=1&os=android&__NStokensig=da69a950da90cedfef80ebce0ca028d17df48314869431ad872333a97994e3e5&token=ecd7394b3383474a9f41098ce22b5b01-1211439612&sig=4070cf6a1c277303ccb700456848c447&client_key=3c2cd3f3");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String html=null;
        JSONArray jsonArray=null;
        if(response.getStatusLine().getStatusCode()==200){
            html = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(html);
            jsonArray = jsonObject.getJSONArray("feeds");
            for(int i=0;i<jsonArray.size();i++){
                asyncMethod.asyncDownLoadVideo(jsonArray,i);
            }
        }
        return jsonArray;
    }

    @GetMapping("demo2")
    public String demo2(){
        System.out.println("你好");
        return "index";
    }

}
