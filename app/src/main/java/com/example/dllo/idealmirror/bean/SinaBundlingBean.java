package com.example.dllo.idealmirror.bean;

/**
 * Created by nan on 16/4/9.
 */
public class SinaBundlingBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"db9b672cc43c24714fe26f9b68d5c27a"}
     */

    private String result;
    private String msg;
    /**
     * token : db9b672cc43c24714fe26f9b68d5c27a
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

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
