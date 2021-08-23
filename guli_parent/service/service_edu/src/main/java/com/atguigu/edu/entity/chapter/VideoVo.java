package com.atguigu.edu.entity.chapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String title;
    private String videoSourceId; //云视频id
}
