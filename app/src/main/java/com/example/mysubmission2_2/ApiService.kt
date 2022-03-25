package com.example.mysubmission2_2

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<ResponseUserDetail>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") username: String
    ): Call<ResponseUserSearch>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>
}