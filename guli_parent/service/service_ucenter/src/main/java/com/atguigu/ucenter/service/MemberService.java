package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.MemberRegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author kou
 * @since 2021-08-11
 */
public interface MemberService extends IService<Member> {

    String userLogin(Member member);

    boolean userRegister(MemberRegisterVo member);

    Member getUser(String memberid);

    Member getUserInfoByOpenId(String openId);

    int savaWxUserInfo(Member newmember);

    Integer getRegisterCountInfo(String day);
}
