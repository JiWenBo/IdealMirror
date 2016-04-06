package com.example.dllo.idealmirror.bean;

/**
 * Created by nan on 16/4/6.
 */
public class UserRegBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"22a36a12023aa370f33f8a79f2a12995","uid":"71"}
     */

    private String result;
    private String msg;
    /**
     * token : 22a36a12023aa370f33f8a79f2a12995
     * uid : 71
     */

    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String token;
        private String uid;

        public void setToken(String token) {
            this.token = token;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public String getUid() {
            return uid;
        }
    }
}
