package com.ayan.openinapp.viewmodels

import ApiResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayan.openinapp.network.RetrofitHelper
import com.ayan.openinapp.network.UrlDetailsAPI
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class DashboardViewModel : ViewModel() {

    private var response: Response<ApiResponse>? = null

    private val _data = MutableLiveData<ApiResponse>()
    val data: LiveData<ApiResponse> get() = _data

    init {
        val urlAPI = RetrofitHelper.getInstance().create(UrlDetailsAPI::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                response = urlAPI.getAllData()
                if (response!!.body() != null) {
                    _data.postValue(response!!.body())
                } else Log.d("???", "error: response null")
            } catch (e: Exception) {
                Log.d("???", "error: ${e.printStackTrace().toString()}")
            }

        }
    }

}
