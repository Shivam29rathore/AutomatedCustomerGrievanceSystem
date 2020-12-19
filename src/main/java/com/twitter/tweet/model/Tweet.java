package com.twitter.tweet.model;

import lombok.Data;

@Data
public  class  Tweet {
    String tweetText;
    String Name;
    String Location;
    String createdAt;
    String sentimentType;
    String sentimentScore;

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSentimentType() {
        return sentimentType;
    }

    public void setSentimentType(String sentimentType) {
        this.sentimentType = sentimentType;
    }

    public String getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(String sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweetURL() {
        return tweetURL;
    }

    public void setTweetURL(String tweetURL) {
        this.tweetURL = tweetURL;
    }

    public String getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(String followerCnt) {
        this.followerCnt = followerCnt;
    }

    public String getVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(String verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getFinalitem() {
        return Finalitem;
    }

    public void setFinalitem(String finalitem) {
        Finalitem = finalitem;
    }

    String userName;
    String tweetURL;
    String followerCnt;
    String verifiedAccount;
    String userInfo;
    String Finalitem;
}
