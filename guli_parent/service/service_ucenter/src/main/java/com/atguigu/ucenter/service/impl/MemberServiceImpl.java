package com.atguigu.ucenter.service.impl;

import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.MemberRegisterVo;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.utils.JwtUtil;
import com.atguigu.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-08-11
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public String userLogin(Member member) {
        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //先判断手机号和密码是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new DefineException(20001,"手机号或者密码不规范，登录失败");
        }

        //接下来判断用户是否存在于数据库中
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();

        memberQueryWrapper.eq("mobile",mobile);

        Member existmember = memberMapper.selectOne(memberQueryWrapper);  //查询一条数据 如果数据库存在多条数据报错

        if (existmember==null){
            throw new DefineException(20001,"用户不存在，登录失败");
        }

        //如果用户存在，接着比较输入密码的MD5转码与数据库中是否一致
        String md5password = existmember.getPassword();

        if (!MD5.encrypt(password).equals(md5password)){
            throw new DefineException(20001,"密码对比错误，登录失败");
        }

        //手机号和密码无误后，接着检验用户是否被禁用
        if (existmember.getIsDisabled()==true){
            throw new DefineException(20001,"用户被禁用，登录失败");
        }

        //手机号、密码、禁用确认无误后，返回token字符串
        String jwtToken = JwtUtil.getJwtToken(existmember.getId(), existmember.getNickname());
        /*
        * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9. 头
        * eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2Mjg3NDIxMDksImV4cCI6MTYyODgyODUwOSwiaWQiOiIxIiwibmlja25hbWUiOiLlsI_kuIkxMjMifQ. 基本信息
        * 8ZYaVRbc0uxDJQGZwNurSNoiq8c79NXrZGRvdOWLZHg 尾
        *  */
        return jwtToken;
    }

    @Override
    public boolean userRegister(MemberRegisterVo member) {

        String nickname = member.getNickname();
        String mobile = member.getMobile();
        String password = member.getPassword();
        String code = member.getCode();

        //先判断是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new DefineException(20001,"手机号、密码、昵称、验证码不规范，注册失败");
        }

        //判断注册用户是否存在
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();

        memberQueryWrapper.eq("mobile",mobile);

        int count = memberMapper.selectCount(memberQueryWrapper);

        if (count > 0){
            throw new DefineException(20001,"用户已注册，注册失败");
        }

        //判断验证码是否正确(既判断类型又判断值)
        if (!redisTemplate.opsForValue().get(mobile).equals(code)){
            throw new DefineException(20001,"验证码错误，注册失败");
        }

        //添加信息到数据库
        Member membertosave = new Member();
        membertosave.setNickname(nickname);
        membertosave.setMobile(mobile);
        membertosave.setIsDisabled(false);
        membertosave.setPassword(MD5.encrypt(password));
        membertosave.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/de47ee9b-7fec-43c5-8173-13c5f7f689b2.png");

        int insert = memberMapper.insert(membertosave);

        if (insert>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Member getUser(String memberid) {

        Member member = memberMapper.selectById(memberid);

        return member;
    }

    @Override
    public Member getUserInfoByOpenId(String openId) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();

        memberQueryWrapper.eq("openid",openId);

        Member member = memberMapper.selectOne(memberQueryWrapper);

        return member;
    }

    @Override
    public int savaWxUserInfo(Member newmember) {
        int count = memberMapper.insert(newmember);
        return count;
    }

    @Override
    public Integer getRegisterCountInfo(String day) {
        Integer count = memberMapper.selectRegisterCount(day);
        return count;
    }
}
