package com.ubayadev.studentproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.studentproject.model.Student

class ListViewModel(application: Application):
    AndroidViewModel(application) {
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()


    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    fun refresh() {
        loadingLD.value = true            // progress bar start muncul
        studentLoadErrorLD.value = false    // tidak ada error

        queue = Volley.newRequestQueue( getApplication() )
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                loadingLD.value = false
                Log.d("showvoley", it)
                val sType = object : TypeToken<List<Student>>() { }.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student>?
                loadingLD.value = false

                Log.d("showvoley", result.toString())

            },
            {
                Log.d("showvoley", it.toString())
                studentLoadErrorLD.value = true
                loadingLD.value = false

            }

        )


        stringRequest.tag = TAG
        queue?.add(stringRequest)

        studentLoadErrorLD.value = false
        loadingLD.value = false
    }
}




