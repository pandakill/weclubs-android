package com.mm.weclubs.data.network.pojo;

/**
 * 创建人： zp
 * 创建时间：2017/7/12
 */

public class WCBannerInfo {

    /**
     * banner_id : 1
     * img_url : http://on633pcgq.bkt.clouddn.com/capture2.png
     * extra : {"scene_id":"999","url":"http://weclubs.net/activity/chengji/new_index.html"}
     * title : 校园文化艺术节
     * content : 校园文化艺术节搞起来啊
     */

    private int banner_id;
    private String img_url;
    private ExtraBean extra;
    private String title;
    private String content;

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class ExtraBean {
        /**
         * scene_id : 999
         * url : http://weclubs.net/activity/chengji/new_index.html
         */

        private String scene_id;
        private String url;

        public String getScene_id() {
            return scene_id;
        }

        public void setScene_id(String scene_id) {
            this.scene_id = scene_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
