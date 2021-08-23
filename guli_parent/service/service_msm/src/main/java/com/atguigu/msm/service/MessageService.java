package com.atguigu.msm.service;

import java.util.HashMap;

public interface MessageService {
    boolean send(HashMap<String, String> params, String phone);
}
