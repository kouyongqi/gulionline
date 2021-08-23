package com.atguigu.ucenter.controller;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.utils.ConstantProperties;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.atguigu.utils.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller //该接口是为了重定向，不返回数据  (该接口暂时还用nginx控制，其他模块是网关gateway)
@RequestMapping("/api/ucenter/wx")
public class WxCodeController {

    @Autowired
    private MemberService memberService;

    //获取登录二维码
    @GetMapping("/login")
    public String login(){

        // 微信开放平台授权baseUrl(跨域)
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantProperties.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new DefineException(20001,"回调地址失败");
        }

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantProperties.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");

        return "redirect:" + qrcodeUrl;
    }

    //扫描成功 跳转到回调地址 http://localhost:8160/api/ucenter/wx/callback?code=011xaAFa1SIkzB0Y77Ia1dNbEC0xaAFu&state=atguigu
    //获取用户信息
    @GetMapping("/callback")
    public String callBackAndGetWxUserInfo(@RequestParam("code") String code, @RequestParam("state") String state){
        //向认证服务器发送请求换取access_token(跨域)
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
        "?appid=%s" +
        "&secret=%s" +
        "&code=%s" +
        "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantProperties.WX_OPEN_APP_ID,
                ConstantProperties.WX_OPEN_APP_SECRET,
                code);

        String accessResult = null;
        try {
            accessResult = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefineException(20001,"获取微信许可证失败");
        }

        //解析accessResult json字符串数据
        Gson gson = new Gson();
        HashMap<String,String> accessHashMap = gson.fromJson(accessResult, HashMap.class);

        String accessToken = accessHashMap.get("access_token");
        String openId = accessHashMap.get("openid");

        //向数据库查询数据
        Member member = memberService.getUserInfoByOpenId(openId);

        if (member==null){
            //访问微信的资源服务器，获取用户信息(当查询的数据不存在时 才向微信服务器发送返回用户信息)
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);

            String userInfoResult = null;
            try {
                userInfoResult = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DefineException(20001,"获取微信用户信息失败");
            }

            //解析userInfoResult json字符串数据
            Gson newgson = new Gson();
            HashMap<String,String> userInfoHashMap = newgson.fromJson(userInfoResult, HashMap.class);

            String nickName = userInfoHashMap.get("nickname");
            String headImgUrl = userInfoHashMap.get("headimgurl");

            //向数据库保存数据
            member = new Member();
            member.setNickname(nickName);
            member.setAvatar(headImgUrl);
            member.setOpenid(openId);
            memberService.savaWxUserInfo(member);
        }

        //跳转到首页面(以地址栏的方式返回token数据)
        String jwtToken = JwtUtil.getJwtToken(member.getId(), member.getNickname());

        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
