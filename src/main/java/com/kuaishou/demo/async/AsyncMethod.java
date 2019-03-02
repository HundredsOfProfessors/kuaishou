package com.kuaishou.demo.async;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuaishou.demo.pojo.Video;
import com.kuaishou.demo.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author chy
 * @date 2019-03-02
 */
@Component
@Slf4j
public class AsyncMethod {
    @Autowired
    private VideoService videoService;

    @Async("mySimpleAsync")
    public void asyncDownLoadVideo(JSONArray jsonArray,int i){
        JSONObject _jsonObject= (JSONObject)jsonArray.get(i);
        JSONArray main_mv_urls = _jsonObject.getJSONArray("main_mv_urls");
        String video_url =null;
        Video video = new Video();
        video.setVideoType("1");
        if(main_mv_urls!=null){
            JSONObject url = (JSONObject)main_mv_urls.get(0);
            String urlStr = url.getString("url");
            video_url = urlStr.split("\\?")[0];
            video.setVideoUrl(video_url);
        }
        String caption = _jsonObject.getString("caption");
        String reg = "[^\u4e00-\u9fa5]";
        caption = caption.replaceAll(reg, " ").replaceAll(" ","");
        video.setCaption(caption);
        String view_count = _jsonObject.getString("view_count");
        video.setViewCount(view_count);
        String fileName="E:\\快手\\"+caption+"_播放量"+view_count+".mp4";
        video.setFileName(fileName);
        log.info("视频{},开始下载",i);
        long startTime = System.currentTimeMillis();
        boolean id_down_ok = httpDownload(video_url, fileName);
        if(id_down_ok){
            long endTime = System.currentTimeMillis();
            long seconds = (endTime - startTime) / 1000;
            log.info("视频{},下载结束,耗时{}秒",i,seconds);
            video.setDownState("1");
        }else{
            log.info("视频{},下载异常",i);
            video.setDownState("0");
        }
        videoService.addVideo(video);

    }

    public static boolean httpDownload(String httpUrl, String saveFile) {
        // 1.下载网络文件
        int byteRead;
        URL url;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return false;
        }

        try {
            //2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            InputStream inStream = conn.getInputStream();
            //3.写入文件
            FileOutputStream fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
