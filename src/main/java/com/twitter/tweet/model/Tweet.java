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
    String userName;
    String tweetURL;
    String followerCnt;
    String verifiedAccount;
    String userInfo;
    String Finalitem;
}
