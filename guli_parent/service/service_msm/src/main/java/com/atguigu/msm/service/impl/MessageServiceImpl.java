package com.atguigu.msm.service.impl;

import com.atguigu.base.exceptionhandler.DefineException;
import com.google.gson.Gson;
import com.atguigu.msm.service.MessageService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public boolean send(HashMap<String, String> params, String phone) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDd4F8k6BzWnnVw84j8G7h13pLaXEfpiJe", "0lFUS9ZjRwA7oEMDoFVVGl00kFUTCgww");

            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            req.setSmsSdkAppId("1400560828");
            req.setSignName("Kou的生活基地");
            req.setTemplateId("1084583");

            String[] templateParamSet1 = {params.get("code")};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);

            // 输出json格式的字符串回包
            //{"SendStatusSet":[{"SerialNo":"2645:325710550416293541720590565","PhoneNumber":"+8617630305653","Fee":1,"SessionContext":"","Code":"Ok","Message":"send success","IsoCode":"CN"}],"RequestId":"76fdbb43-f9be-4a81-b2b6-1ac18993ba1d"}
            //System.out.println(SendSmsResponse.toJsonString(resp));
            SendStatus sendStatus = resp.getSendStatusSet()[0];

            if (sendStatus.getMessage().equals("send success")){
                return true;
            }else {
                throw new DefineException(20001,"短信发送有误");
            }
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
