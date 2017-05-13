package com.mm.weclubs.app.comment;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCCommentListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午4:18
 * 描述:
 */

public interface WCCommentView extends MVPView {

    void refreshCommentList(ArrayList<WCCommentListInfo> list);

    void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore);
}
