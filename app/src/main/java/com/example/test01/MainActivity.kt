package com.example.test01

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test01.scaleFragment
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_rotate.*
import kotlinx.android.synthetic.main.fragment_scale.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //先给ViewPager一个适配器，让他知道都有几页，每一页创建什么Fragment
        /*viewPager2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount() = 3
            override fun createFragment(position: Int) =
                when(position){
                    0 -> rotateFragment()
                    1 -> scaleFragment()
                    else -> translateFragment()
                }
        }

        //然后将viewPager和TabLayout联系起来
        TabLayoutMediator(tabLayout,viewPager2){tab, position ->
            when(position){
                0 -> tab.text = "旋转"
                1 -> tab.text = "缩放"
                else -> tab.text = "移动"
            }
        }.attach()*/
        /*viewPager2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount() = 3
            override fun createFragment(position: Int) =
                    when(position){
                        0 -> rotateFragment()
                        1 -> scaleFragment()
                        else -> translateFragment()
                    }
        }

        TabLayoutMediator(tabLayout,viewPager2){tab,position ->
            when(position){
                0 -> "旋转"
                1 -> "缩放"
                else -> "移动"
            }
        }.attach()
*/

    }
}