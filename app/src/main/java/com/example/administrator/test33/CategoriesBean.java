package com.example.administrator.test33;

import java.util.List;

/**
 * Created by zhiyuan on 2017/9/2.
 */

public class CategoriesBean {

    /**
     * lid : 0100
     * lname : 语言
     * hobbies : [{"lid":"0101","lname":"普通话","flid":"0100"},{"lid":"0102","lname":"英语","flid":"0100"},{"lid":"0103","lname":"日语","flid":"0100"},{"lid":"0104","lname":"俄语","flid":"0100"},{"lid":"0105","lname":"法语","flid":"0100"},{"lid":"0106","lname":"韩语","flid":"0100"},{"lid":"0107","lname":"德语","flid":"0100"},{"lid":"0108","lname":"对外汉语","flid":"0100"},{"lid":"0109","lname":"西班牙语","flid":"0100"},{"lid":"0110","lname":"阿拉伯语","flid":"0100"},{"lid":"0111","lname":"意大利语","flid":"0100"}]
     */

    private String lid;
    private String lname;
    private List<HobbiesBean> hobbies;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public List<HobbiesBean> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbiesBean> hobbies) {
        this.hobbies = hobbies;
    }

    public static class HobbiesBean {
        /**
         * lid : 0101
         * lname : 普通话
         * flid : 0100
         */

        private String lid;
        private String lname;
        private String flid;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getFlid() {
            return flid;
        }

        public void setFlid(String flid) {
            this.flid = flid;
        }
    }
}
