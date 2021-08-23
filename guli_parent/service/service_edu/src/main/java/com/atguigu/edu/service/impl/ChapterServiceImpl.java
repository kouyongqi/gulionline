package com.atguigu.edu.service.impl;

import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.chapter.ChapterVo;
import com.atguigu.edu.entity.chapter.VideoVo;
import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.entity.subject.TwoSubject;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.ChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<ChapterVo> getChapter(String id) {
        //查询course_id下所有章节和小节（有条件）
        QueryWrapper<Chapter> ChapterQuery= new QueryWrapper<>();

        ChapterQuery.eq("course_id",id);

        List<Chapter> Chapters = baseMapper.selectList(ChapterQuery);

        QueryWrapper<Video> VideoQuery= new QueryWrapper<>();

        VideoQuery.eq("course_id",id);

        List<Video> Videos = videoMapper.selectList(VideoQuery);


        //封装章节数据
        List<ChapterVo> finalChapterVo = new ArrayList<>();

        for (Chapter chapter : Chapters){

            ChapterVo newChapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,newChapterVo);

            finalChapterVo.add(newChapterVo);

            List<VideoVo> newchildren = new ArrayList<>();

            //封装小节数据到章节中
            for (Video video : Videos){
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo newVideoVo = new VideoVo();
                    BeanUtils.copyProperties(video,newVideoVo);
                    newchildren.add(newVideoVo);
                }
            }

            newChapterVo.setChildren(newchildren);
        }

        return finalChapterVo;
    }

    @Override
    public boolean deleteChapterInfo(String chapterid) {
        //查询章节下是否有小节
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("chapter_id",chapterid);

        Integer integer = videoMapper.selectCount(queryWrapper);

        if (integer>0){
            throw new DefineException(20001,"该章节下有小节，无法删除");
        }else {
            int i = baseMapper.deleteById(chapterid);
            //根据删除后返回的数量判断是否删除啦
            return i>0;
        }
    }


}
