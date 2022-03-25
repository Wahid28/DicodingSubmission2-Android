package com.example.mysubmission2_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel(), ListUserAdapter.OnItemClickCallback {

    private val _dataUser = MutableLiveData<ArrayList<UserResponse>>()
    val dataUser: LiveData<ArrayList<UserResponse>> = _dataUser

    private val _listFollower = MutableLiveData<ArrayList<UserResponse>>()
    val listFollower: LiveData<ArrayList<UserResponse>> = _listFollower

    private val _listFollowing = MutableLiveData<ArrayList<UserResponse>>()
    val listFollowing: LiveData<ArrayList<UserResponse>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUser(USERNAME)
        MoveDataWithActivity.USERNAME?.let { findFollower(it) }
        MoveDataWithActivity.USERNAME?.let { findFollowing(it) }
    }

    //Menampilkan User
    private fun findUser(username: String) {
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<ResponseUserSearch> {
            override fun onResponse(
                call: Call<ResponseUserSearch>,
                response: Response<ResponseUserSearch>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _dataUser.value = responseBody.items
                    }
                } else{
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseUserSearch>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    //    Mencari Follower
    private fun findFollower(username: String) {
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponse>>,
                response: Response<ArrayList<UserResponse>>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listFollower.value = responseBody
                    }
                } else{
                    Log.e(Follower.TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable){
                _isLoading.value = false
                Log.e(Follower.TAG, "onFailure: ${t.message}")
            }
        })
    }

//    Mencari Following
    private fun findFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponse>>,
                response: Response<ArrayList<UserResponse>>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listFollowing.value = responseBody
                    }
                } else{
                    Log.e(Follower.TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable){
                _isLoading.value = false
                Log.e(Follower.TAG, "onFailure: ${t.message}")
            }
        })
    }

    //Object yang akan diambil
    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "a"
    }

    override fun onItemClicked(data: ResponseUserDetail) {
        TODO("Not yet implemented")
    }
}