package com.study.nobest.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class PoemListInfo {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * totalCount : 50
     * listData : [{"userId":"","userNickName":"","userPicUrl":"","publishedTime":"","userSex":1,"content":"","contentPics":["url1","url2"],"collectStatus":false,"praiseStatus":false,"praiseNum":100,"commentNum":100,"flowerNum":100,"poemId":""}]
     */

    private int totalCount;
    /**
     * userId :
     * userNickName :
     * userPicUrl :
     * publishedTime :
     * userSex : 1
     * content :
     * contentPics : ["url1","url2"]
     * collectStatus : false
     * praiseStatus : false
     * praiseNum : 100
     * commentNum : 100
     * flowerNum : 100
     * poemId :
     */

    private List<ListDataEntity> listData;


    public List<ListDataEntity> getListData() {
        return listData;
    }

    public void setListData(List<ListDataEntity> listData) {
        this.listData = listData;
    }

    public static class ListDataEntity {
        private String userId;
        private String userNickName;
        private String userPicUrl;
        private String publishedTime;
        private int userSex;
        private String content;
        private boolean collectStatus;
        private boolean praiseStatus;
        private String praiseNum;
        private String commentNum;
        private String flowerNum;
        private String poemId;
        private List<String> contentPics;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserPicUrl() {
            return userPicUrl;
        }

        public void setUserPicUrl(String userPicUrl) {
            this.userPicUrl = userPicUrl;
        }

        public String getPublishedTime() {
            return publishedTime;
        }

        public void setPublishedTime(String publishedTime) {
            this.publishedTime = publishedTime;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(boolean collectStatus) {
            this.collectStatus = collectStatus;
        }

        public boolean isPraiseStatus() {
            return praiseStatus;
        }

        public void setPraiseStatus(boolean praiseStatus) {
            this.praiseStatus = praiseStatus;
        }

        public String getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(String praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getFlowerNum() {
            return flowerNum;
        }

        public void setFlowerNum(String flowerNum) {
            this.flowerNum = flowerNum;
        }

        public String getPoemId() {
            return poemId;
        }

        public void setPoemId(String poemId) {
            this.poemId = poemId;
        }

        public List<String> getContentPics() {
            return contentPics;
        }

        public void setContentPics(List<String> contentPics) {
            this.contentPics = contentPics;
        }
    }
}
