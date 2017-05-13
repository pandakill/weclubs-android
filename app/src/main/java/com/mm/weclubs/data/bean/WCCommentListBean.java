package com.mm.weclubs.data.bean;

import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午4:22
 * 描述:  评论列表接口的实体类
 */

public class WCCommentListBean implements Serializable {

    private ArrayList<WCCommentListInfo> comment;
    private int has_more;

    public ArrayList<WCCommentListInfo> getComment() {
        return comment;
    }

    public void setComment(ArrayList<WCCommentListInfo> comment) {
        this.comment = comment;
    }

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
