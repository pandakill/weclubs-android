package com.mm.weclubs.app.club_detail;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCHonorInfo;
import com.mm.weclubs.data.network.pojo.WCStudentInfo;

import java.util.List;

public interface WCClubDetailContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void loadDetail(long club_id);

        void apply();

        void showQRCode();

        void clickMember(int viewId);
    }

    interface View extends MVPView{

        void setAvatar(String path);

        void setClubName(String name);

        /**
         *
         */
        void setSlogan(String slogan);

        void setLevel(int level);

        void setAttr(String attr);

        void setHonorList(List<WCHonorInfo> honors);

        void setMemberList(List<WCStudentInfo> students);

        void showQRView(String qrPath);
    }
}
