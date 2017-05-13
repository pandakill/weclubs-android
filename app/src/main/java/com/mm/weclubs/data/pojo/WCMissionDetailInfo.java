package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/14 上午12:04
 * 描述:
 */

public class WCMissionDetailInfo extends WCMissionListInfo implements Serializable {

    private ArrayList<MissionParticipant> participant;
    private ArrayList<MissionChild> child;

    public ArrayList<MissionParticipant> getParticipant() {
        return participant;
    }

    public void setParticipant(ArrayList<MissionParticipant> participant) {
        this.participant = participant;
    }

    public ArrayList<MissionChild> getChild() {
        return child;
    }

    public void setChild(ArrayList<MissionChild> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }

    public class MissionParticipant implements Serializable {
        private String student_name;
        private long student_id;
        private String avatar_url;

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public long getStudent_id() {
            return student_id;
        }

        public void setStudent_id(long student_id) {
            this.student_id = student_id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        @Override
        public String toString() {
            return JsonHelper.getJsonStrFromObj(this);
        }
    }

    public class MissionChild implements Serializable {
        private long mission_id;
        private int finish;
        private String content;
        private int has_child;
        private ArrayList<MissionParticipant> participant;

        public long getMission_id() {
            return mission_id;
        }

        public void setMission_id(long mission_id) {
            this.mission_id = mission_id;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getHas_child() {
            return has_child;
        }

        public void setHas_child(int has_child) {
            this.has_child = has_child;
        }

        public ArrayList<MissionParticipant> getParticipant() {
            return participant;
        }

        public void setParticipant(ArrayList<MissionParticipant> participant) {
            this.participant = participant;
        }

        @Override
        public String toString() {
            return JsonHelper.getJsonStrFromObj(this);
        }
    }
}
