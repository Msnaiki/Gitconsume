package com.snaiki.consumegit.model;

import com.google.gson.annotations.SerializedName;

public class UserGit {
    public int id;
    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;
    public int score;
}
