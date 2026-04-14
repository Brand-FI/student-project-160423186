package com.ubayadev.studentproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.studentproject.model.Student

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    private var TAG = "volley_tag"
    private var queue: RequestQueue? = null


    fun fetch(id: String) {
        val url = "https://www.jsonkeeper.com/b/LLMW"

        /*val student1 = Student(
               "16055", "Nonie", "1998/03/28", "5718444778",
               "http://dummyimage.com/75x100.jpg/cc0000/ffffff"
           ) */
        var sr = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                val students = result as ArrayList<Student>
                studentLD.value = students.find { it.id == id }
            }, {
                Log.e("volley_status", it.message.toString())
            },
        )
    }
}







