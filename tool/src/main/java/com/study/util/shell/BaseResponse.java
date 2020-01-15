package com.study.util.shell;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by shange on 2017/9/22.
 */
public class BaseResponse {

    public BaseResponse() {
    }

    public BaseResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse(JSONObject jsonObject) {
        this.status = jsonObject.getIntValue("status");
        this.message = jsonObject.getString("message");
        this.data = jsonObject.getString("data");
    }

    // 状态码
    private Integer status;
    // 结果描述
    private String message;
    // 耗时
    private Long cost;
    // 数据
    private String data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        jsonObject.put("cost", cost);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }
}