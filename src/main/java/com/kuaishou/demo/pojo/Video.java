package com.kuaishou.demo.pojo;

import lombok.Data;

/**
 * @author chy
 * @date 2019-03-02
 */
@Data
public class Video {
    private int id;
    private String videoType;
    private String videoUrl;
    private String viewCount;
    private String caption;
    private String downState;
    private String fileName;
    private String createDate;
}
