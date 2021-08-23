package com.atguigu.edu.controller;


import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.chapter.ChapterVo;
import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/getAllChapterVo/{courseid}")
    public Result getAllChapterVoByCourseId(@PathVariable String courseid){

        List<ChapterVo> ChapterVos = chapterService.getChapter(courseid);

        return Result.ok().data("chaptervos",ChapterVos);
    }


    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody Chapter chapter){
        boolean save = chapterService.save(chapter);

        if (save==true){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/deleteChapter/{chapterid}")
    public Result deleteChapter(@PathVariable String chapterid){

        boolean delete = chapterService.deleteChapterInfo(chapterid);

        if (delete==true){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getChapter/{chapterid}")
    public Result getChapter(@PathVariable String chapterid){
        Chapter chapter = chapterService.getById(chapterid);
        if (chapter!=null){
            return Result.ok().data("chapter",chapter);
        }else {
            return Result.error();
        }
    }

    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody Chapter chapter){
        boolean update = chapterService.updateById(chapter);

        if (update==true){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

