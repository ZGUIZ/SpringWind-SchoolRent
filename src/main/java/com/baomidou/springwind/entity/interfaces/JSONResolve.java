package com.baomidou.springwind.entity.interfaces;

import com.alibaba.fastjson.JSONObject;

public interface JSONResolve {
    JSONResolve getObjectFromJson(String json);
    JSONResolve getObjectFromJsonObject(JSONObject jsonObject);
}
