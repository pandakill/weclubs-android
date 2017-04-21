package com.mm.weclubs.data.pojo;

import com.mm.weclubs.data.pojo.RequestBean.ClientBean.ExBean;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:44
 * 描述:
 */

public class RequestBean {

    /**
     * id : 1234567890
     * client : {"caller":"chrome_test","version":"v2.1","date":"1222","ex":{"sc":"720,1280","dv":"xiaomi_os","uid":"122fff","sf":"pp","os":"android"}}
     * encrypt : md5
     * sign : fafafafafafafa
     * data : {}
     */

    private String id;
    /**
     * caller : chrome_test
     * version : v2.1
     * date : 1222
     * ex : {"sc":"720,1280","dv":"xiaomi_os","uid":"122fff","sf":"pp","os":"android"}
     */

    private ClientBean client;
    private String encrypt;
    private String sign;
    private Object data;

    public static RequestBean getRequestData(Object object) {
        RequestBean requestBean = new RequestBean();
        requestBean.setData(object);

        requestBean.setId("12234455555");
        requestBean.setEncrypt("md5");
        requestBean.setSign("fafadfdsafdsaf");

        ClientBean clientBean = new ClientBean();
        clientBean.setCaller("chrome_test");
        clientBean.setVersion("v2.1");
        clientBean.setDate(System.currentTimeMillis() + "");

        ExBean exBean = new ExBean();
        exBean.setDv("chrome_test");
        exBean.setSc("720,1280");
        exBean.setUid("12dfaf990");
        exBean.setSf("pp");
        exBean.setOs("android");
        clientBean.setEx(exBean);

        requestBean.setClient(clientBean);

        return requestBean;
    }

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

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
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
