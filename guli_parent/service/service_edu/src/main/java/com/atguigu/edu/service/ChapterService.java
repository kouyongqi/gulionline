package com.atguigu.edu.service;

import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapter(String id);

    boolean deleteChapterInfo(String chapterid);
}
