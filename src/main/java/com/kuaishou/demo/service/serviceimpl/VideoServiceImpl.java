package com.kuaishou.demo.service.serviceimpl;

import com.kuaishou.demo.mapper.VideoMapper;
import com.kuaishou.demo.pojo.Video;
import com.kuaishou.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chy
 * @date 2019-03-02
 */
@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void addVideo(Video video) {
        videoMapper.addVideo(video);
    }
}
