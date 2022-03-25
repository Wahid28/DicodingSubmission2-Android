package com.example.mysubmission2_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2_2.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Following : Fragment(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: FragmentFollowingBinding

//    Membuat Tampilan
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container,false)
        return binding.root
    }

//    Setelah Tampilan terbuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val follower = activity?.intent?.getParcelableExtra<ResponseUserDetail>(Follower.USERNAME)

        Toast.makeText(requireContext(), "Hello ${follower?.login}", Toast.LENGTH_LONG).show()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.listFollowing.observe(viewLifecycleOwner, {listFollowing ->
            getDetailUser(listFollowing)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, {showLoading(it)})

    }

//    Mendapatkan Detail Following User
    private fun getDetailUser(user: ArrayList<UserResponse>){
        val listDetailUser = arrayListOf<ResponseUserDetail>()
        for((index, data) in user.withIndex()){
            val client = ApiConfig.getApiService().getUser(data.login)
            client.enqueue(object : Callback<ResponseUserDetail> {
//                Setelah Respons diterima
                override fun onResponse(
                    call: Call<ResponseUserDetail>,
                    response: Response<ResponseUserDetail>
                ){
                    showLoading(false)
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            listDetailUser.add(responseBody)
                            if(index == user.size - 1){
                                val layoutManager = LinearLayoutManager(activity)
                                binding.rvFollowing.layoutManager = layoutManager

                                val adapter = ListUserAdapter(listDetailUser, this@Following)
                                binding.rvFollowing.adapter = adapter
                            }
                        }
                    } else{
                        Log.e(TAG,"onFailure: ${response.message()}")
                        Toast.makeText(requireContext(), "this is Error", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable){
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                    Toast.makeText(requireContext(), "this is Failure", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

//    Menunjukkan dan menghilangkan Loading
    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    Fungsi saat ada Akun Following yang diklik
    override fun onItemClicked(data: ResponseUserDetail) {
        val intentToDetail = Intent(activity, MoveDataWithActivity::class.java)
        intentToDetail.putExtra(MoveDataWithActivity.EXTRA_USER, data)
        startActivity(intentToDetail)
    }

//    Objek Pendamping
    companion object {
        const val TAG = "FollowingFragment"
    }


}