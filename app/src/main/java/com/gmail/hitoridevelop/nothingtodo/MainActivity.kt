package com.gmail.hitoridevelop.nothingtodo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gmail.hitoridevelop.nothingtodo.api.BoredApi
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var  textView: TextView
    lateinit var response: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        val viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Do background tasks...
                try {
                    response = BoredApi.retrofitService.getRandomActivity().toString()
                } catch (e: Exception) {
                    println(e.message)
                }
                withContext(Dispatchers.Main){
                    //Update UI

                    textView.text = response
                }
            }
        }


    }
}