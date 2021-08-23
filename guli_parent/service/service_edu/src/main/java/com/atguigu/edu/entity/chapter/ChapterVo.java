package com.atguigu.edu.entity.chapter;

import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.subject.TwoSubject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChapterVo {

    private String id;
    private String title;

    List<VideoVo> children = new ArrayList<>();
}
