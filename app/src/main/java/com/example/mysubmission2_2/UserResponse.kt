package com.example.mysubmission2_2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("events_url")
	val eventsUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("node_id")
	val nodeId: String
)

data class ResponseUserSearch(
	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: ArrayList<UserResponse>
)

@Parcelize
data class ResponseUserDetail(

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("login")
	val login: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int?,

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("following")
	val following: Int?,

): Parcelable