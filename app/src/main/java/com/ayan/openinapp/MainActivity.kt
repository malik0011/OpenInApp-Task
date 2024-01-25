package com.ayan.openinapp

import ApiResponse
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayan.openinapp.adapters.InfoBoxAdapter
import com.ayan.openinapp.adapters.LinkPagerAdapter
import com.ayan.openinapp.databinding.ActivityMainBinding
import com.ayan.openinapp.models.Info
import com.ayan.openinapp.viewmodels.DashboardViewModel
import com.google.android.material.tabs.TabLayout
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DashboardViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Used WindowInsetsController to hide the status bar
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGreeting.text = getGreeting()
        viewModel.data.observe(this) {
            binding.pBar.isVisible = false
            setUpInfoAdapter(it)
            setUpTabLayout()
            setUpGraphView()
        }
        setUpListner()

    }

    private fun setUpListner() {
        binding.llWhatsapp.setOnClickListener {
            openWhatsAppWithMessage(
                applicationContext,
                viewModel.data.value?.supportWhatsappNumber!!
            )
        }
    }

    private fun setUpInfoAdapter(data: ApiResponse) {
        binding.rcvHorizontal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rcvHorizontal.adapter = InfoBoxAdapter(
            arrayListOf(
                Info(R.drawable.img_today_click, data.todayClicks.toString(), "Today's Click"),
                Info(R.drawable.img_pin, data.topLocation, "Top Location"),
                Info(R.drawable.img_globe, data.topSource, "Top Source"),
                Info(R.drawable.img_time, "11:00 - 12:00", "Best Time")
            )
        )
    }

    private fun setUpTabLayout() {
        val adapter = LinkPagerAdapter(
            supportFragmentManager,
            lifecycle,
            viewModel.data.value?.data?.topLinks,
            viewModel.data.value?.data?.recentLinks
        )
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Top Links"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Recent Links"))
        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hourOfDay) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private fun setUpGraphView() {
        val mp = viewModel.data.value?.data?.overallURLChart
        val dataList = ArrayList<DataPoint>()
        val pattern = "yyyy-MM-dd"
        if (mp != null) {
            for ((key, value) in mp) {
                dataList.add(
                    DataPoint(
                        SimpleDateFormat(pattern).parse(key) as Date,
                        value.toDouble()
                    )
                )
            }
        }

        val series = LineGraphSeries(dataList.toTypedArray())
        binding.graphView.removeAllSeries()
        binding.graphView.addSeries(series)

        // set date label formatter
        binding.graphView.gridLabelRenderer.labelFormatter =
            DateAsXAxisLabelFormatter(applicationContext);
        binding.graphView.gridLabelRenderer.numHorizontalLabels = 3; // only 4 because of the space

        // activate horizontal zooming and scrolling
        binding.graphView.viewport.isScalable = true;

        // activate horizontal scrolling
        binding.graphView.viewport.isScrollable = true;
    }

    private fun openWhatsAppWithMessage(context: Context, phoneNumber: String) {
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        try {
            val pm = context.packageManager
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(
                this,
                "Whatsapp app not installed in your phone",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

}