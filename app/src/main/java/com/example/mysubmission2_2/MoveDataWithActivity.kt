package com.example.mysubmission2_2

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmission2_2.databinding.ActivityMoveWithDataBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoveDataWithActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = SectionsPagerAdapter(this)
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setData()

        supportActionBar?.title = USERNAME
    }

    private fun setData(){
        val user = intent.getParcelableExtra<ResponseUserDetail>(EXTRA_USER)
        Log.d("Detail Data", user?.name.toString())

        USERNAME = user?.login

        binding.namaUser.text = user?.name
        binding.followersUser.text = user?.followers.toString()
        binding.followingUser.text = user?.following.toString()
        binding.usernameUser.text = getString(R.string.username_tag, user?.login)
        binding.locationUser.text = getString(R.string.location_tag, user?.location)
        binding.companyUser.text = getString(R.string.company_tag, user?.company)
        Glide.with(this)
            .load(user?.avatarUrl)
            .into(binding.photoUser)
        binding.repositoryUser.text = user?.publicRepos.toString()
    }

    companion object{
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        var USERNAME: String? = "username"
    }
}