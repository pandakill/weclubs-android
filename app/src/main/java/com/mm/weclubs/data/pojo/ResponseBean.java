package com.mm.weclubs.data.pojo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:34
 * 描述:
 */

public class ResponseBean<T> {

    /**
     * result_code : 200
     * result_msg : 请求成功.
     * sys_time : 1486431081896
     * data : {}
     */

    private int result_code;
    private String result_msg;
    private long sys_time;
    private T data;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "result_code=" + result_code +
                ", result_msg='" + result_msg + '\'' +
                ", sys_time=" + sys_time +
                ", data=" + data +
                '}';
    }
}
