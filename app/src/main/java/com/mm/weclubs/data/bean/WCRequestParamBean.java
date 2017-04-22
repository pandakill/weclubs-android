package com.mm.weclubs.data.bean;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午7:43
 * 描述:  请求参数的模型
 */

public class WCRequestParamBean {

    private String id;

    private ClientBean client;
    private String sign;
    private Object data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class ClientBean {
        private String caller;
        private String version;
        private String date;

        private ExBean ex;

        public String getCaller() {
            return caller;
        }

        public void setCaller(String caller) {
            this.caller = caller;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ExBean getEx() {
            return ex;
        }

        public void setEx(ExBean ex) {
            this.ex = ex;
        }

        public static class ExBean {
            private String sc;
            private String dv;
            private String uid;
            private String sf;
            private String os;

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getDv() {
                return dv;
            }

            public void setDv(String dv) {
                this.dv = dv;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getSf() {
                return sf;
            }

            public void setSf(String sf) {
                this.sf = sf;
            }

            public String getOs() {
                return os;
            }

            public void setOs(String os) {
                this.os = os;
            }
        }
    }
}
