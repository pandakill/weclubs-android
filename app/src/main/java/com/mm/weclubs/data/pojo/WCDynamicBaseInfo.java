package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 下午10:50
 * 描述:
 */

public class WCDynamicBaseInfo implements Serializable {

    private static final long serialVersionUID = 1024L;

    private String dynamic_type;
    private SponsorObj sponsor;

    public String getDynamic_type() {
        return dynamic_type;
    }

    public void setDynamic_type(String dynamic_type) {
        this.dynamic_type = dynamic_type;
    }

    public SponsorObj getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorObj sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }

    public class SponsorObj implements Serializable {
        private long sponsor_id;
        private String sponsor_avatar;
        private String sponsor_name;
        private String sponsor_type;

        public long getSponsor_id() {
            return sponsor_id;
        }

        public void setSponsor_id(long sponsor_id) {
            this.sponsor_id = sponsor_id;
        }

        public String getSponsor_avatar() {
            return sponsor_avatar;
        }

        public void setSponsor_avatar(String sponsor_avatar) {
            this.sponsor_avatar = sponsor_avatar;
        }

        public String getSponsor_name() {
            return sponsor_name;
        }

        public void setSponsor_name(String sponsor_name) {
            this.sponsor_name = sponsor_name;
        }

        public String getSponsor_type() {
            return sponsor_type;
        }

        public void setSponsor_type(String sponsor_type) {
            this.sponsor_type = sponsor_type;
        }

        @Override
        public String toString() {
            return JsonHelper.getJsonStrFromObj(this);
        }
    }
}
