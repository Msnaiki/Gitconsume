package com.snaiki.consumegit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserGitResponse {
    @SerializedName("total_count")
    public int totalCount;
    @SerializedName("items")
    public List <UserGit> users =  new ArrayList<>();
}
