package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午6:24
 * 描述:
 */

public class WCMeetingDetailInfo extends WCMeetingListInfo implements Serializable {

    private ArrayList<Leader> leader;

    public ArrayList<Leader> getLeader() {
        return leader;
    }

    public void setLeader(ArrayList<Leader> leader) {
        this.leader = leader;
    }

    public class Leader implements Serializable {
        private long student_id;
        private String student_name;
        private String department_name;
        private String job_name;
        private String mobile;
        private String avatar_url;

        public long getStudent_id() {
            return student_id;
        }

        public void setStudent_id(long student_id) {
            this.student_id = student_id;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
