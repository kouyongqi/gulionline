package com.atguigu.ucenter.controller;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.MemberRegisterVo;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.utils.JwtUtil;
import com.atguigu.utils.MemberOrderVo;
import com.atguigu.utils.MemberVo;
import com.atguigu.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody Member member){

        //如果登录成功返回一个token 这个token中包含用户的信息 可用于别的模块登录
        String token = memberService.userLogin(member);

        if (token!=null){
            return Result.ok().data("token",token);
        }else {
            return Result.error();
        }
    }


    @PostMapping("/register")
    public Result register(@RequestBody MemberRegisterVo memberRegisterVo){

        boolean isregister = memberService.userRegister(memberRegisterVo);

        if (isregister){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getuserinfo")
    public Result getUserInfo(HttpServletRequest request){

        String memberid = JwtUtil.getMemberIdByJwtToken(request);

        if (memberid!=null){
            Member member = memberService.getUser(memberid);
            return Result.ok().data("memberinfo",member);
        }else {
            return Result.error();
        }

    }

    @GetMapping("/getmemberinfo/{memberid}")
    public MemberVo getMemberVoInfo(@PathVariable String memberid){

        MemberVo memberVo = new MemberVo();

        Member member = memberService.getById(memberid);

        BeanUtils.copyProperties(member,memberVo);

        return memberVo;
    }

    @GetMapping("/memberorderInfo/{memberid}")
    public MemberOrderVo getMemberOrderInfo(@PathVariable String memberid){

        MemberOrderVo memberOrderVo = new MemberOrderVo();

        Member member = memberService.getById(memberid);

        BeanUtils.copyProperties(member,memberOrderVo);

        return memberOrderVo;
    }

    @GetMapping("/getRegisterCount/{day}")
    public Integer getRegisterCount(@PathVariable String day){

        Integer count = memberService.getRegisterCountInfo(day);

        if (count>0){
            return count;
        }else {
            throw new DefineException(20001,"获取注册数量失败");
        }
    }

}

