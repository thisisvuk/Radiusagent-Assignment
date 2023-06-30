package com.example.assignmentradiusagent

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.assignmentradiusagent.model.ApiService
import com.example.assignmentradiusagent.model.DataModelImpl
import com.example.assignmentradiusagent.model.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRefreshWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    // Override the doWork() function which defines the work to be done in the background
    override fun doWork(): Result {

        // Fetching data from API
        GlobalScope.launch {
            val apiService = Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/iranjith4/ad-assignment/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

            val db = AppDatabase.getDatabase(applicationContext)
            DataModelImpl(apiService, db).getFacilities()
        }


        return Result.success()
    }
}