package com.example.mysubmission2_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2_2.databinding.FragmentFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Follower : Fragment(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: FragmentFollowerBinding

    //    Membuat Tampilan Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root

    }

    //    Setelah Tampilan Terbuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val follower = activity?.intent?.getParcelableExtra<ResponseUserDetail>(USERNAME)

        Toast.makeText(requireContext(), "Hello ${follower?.login}", Toast.LENGTH_LONG).show()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.listFollower.observe(viewLifecycleOwner, { listFollower ->
            getDetailUser(listFollower)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, { showLoading(it) })

    }

    //    Mendapatkan Detail Follower User
    private fun getDetailUser(user: ArrayList<UserResponse>) {
        val listDetailUser = arrayListOf<ResponseUserDetail>()
        for ((index, data) in user.withIndex()) {
            val client = ApiConfig.getApiService().getUser(data.login)
            client.enqueue(object : Callback<ResponseUserDetail> {
                override fun onResponse(
                    call: Call<ResponseUserDetail>,
                    response: Response<ResponseUserDetail>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            listDetailUser.add(responseBody)
                            if (index == user.size - 1) {
                                val layoutManager = LinearLayoutManager(activity)
                                binding.rvFollower.layoutManager = layoutManager

                                val adapter = ListUserAdapter(listDetailUser, this@Follower)
                                binding.rvFollower.adapter = adapter
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        Toast.makeText(requireContext(), "this is Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                    Toast.makeText(requireContext(), "this is Failure", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    //    Menunjukkan atau Menghilangkan Loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    Fungsi saat Follower diklik
    override fun onItemClicked(data: ResponseUserDetail) {
        val intentToDetail = Intent(activity, MoveDataWithActivity::class.java)
        intentToDetail.putExtra(MoveDataWithActivity.EXTRA_USER, data)
        startActivity(intentToDetail)
    }

//    Object Pendamping
    companion object {
        const val USERNAME = MoveDataWithActivity.EXTRA_USER
        const val TAG = "FollowerFragment"
    }
}
