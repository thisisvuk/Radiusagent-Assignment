package com.example.assignmentradiusagent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.assignmentradiusagent.model.ApiService
import com.example.assignmentradiusagent.model.DataModelImpl
import com.example.assignmentradiusagent.model.database.AppDatabase
import com.example.assignmentradiusagent.model.dataclass.Facilities
import com.example.assignmentradiusagent.preseterr.DataPresenter
import com.example.assignmentradiusagent.preseterr.DataPresenterImpl
import com.example.assignmentradiusagent.ui.Facility
import com.example.assignmentradiusagent.ui.Options
import com.example.assignmentradiusagent.ui.theme.AssignmentRadiusAgentTheme
import com.example.assignmentradiusagent.view.DataView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity(), DataView {

    private lateinit var presenter: DataPresenter
    private lateinit var facilities: MutableState<List<Facilities>>
    private lateinit var exclusions: MutableState<Map<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creating instance of ApiService using Retrofit
        val apiService = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/iranjith4/ad-assignment/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Getting instance of the AppDatabase
        val db = AppDatabase.getDatabase(applicationContext)

        // Creating instance of DataModelImpl and DataPresenterImpl
        val dataModel = DataModelImpl(apiService, db)
        presenter = DataPresenterImpl(dataModel, this)

        // Calling getFacilities() on the presenter to fetch and display facilities
        presenter.getFacilities()

        // Setting Constraints and PeriodicWorkRequest using WorkManager
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(
            1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        setContent {
            AssignmentRadiusAgentTheme {

                facilities = remember {
                    mutableStateOf(emptyList())
                }

                exclusions = remember {
                    mutableStateOf(emptyMap())
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val list = remember {
                        mutableStateOf(facilities)
                    }

                    MainScreen(facilities = list.value)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoading() {
        Log.d("Logging", "Loading....")
    }

    override fun hideLoading() {
        Log.d("Logging", "Loading Stopped")
    }


    override fun displayFacilities(
        facilities: List<Facilities>,
        exclusionMap: MutableMap<String, String>
    ) {
        this.facilities.value = facilities
        this.exclusions.value = exclusionMap
    }

    override fun showError(errorMessage: String) {
        Log.d("Logging", errorMessage)

    }


    @Composable
    fun MainScreen(facilities: MutableState<List<Facilities>>) {
        LazyColumn(
            modifier = Modifier.padding(20.dp)
        ) {
            items(facilities.value.size) { index ->
                val facility = facilities.value[index]
                Facility(name = facility.name)
                facility.options.forEach { option ->
                    Options(
                        name = option.name,
                        icon = option.icon,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        isSelected = option.id == facility.selectedOptionId,
                        isDisabled = option.id == facility.disabledOptionId
                    ) {
                        val pair = facility.facility_id + "," + option.id
                        val disabledPair =
                            exclusions.value.entries.firstOrNull { it.key == pair || it.value == pair }
                                ?.let { if (it.key == pair) it.value else it.key }
                        val disablesFacilityId = disabledPair?.split(",")?.get(0)
                        val disablesOptionId = disabledPair?.split(",")?.get(1)
                        facilities.value = facilities.value.map { f ->
                            when (f.facility_id) {
                                facility.facility_id -> {
                                    f.copy(
                                        selectedOptionId = option.id
                                    )
                                }
                                disablesFacilityId -> {
                                    f.copy(
                                        disabledOptionId = disablesOptionId ?: ""
                                    )
                                }
                                else -> {
                                    f.copy(disabledOptionId = "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        AssignmentRadiusAgentTheme {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Facility(name = "Apartment")
                Options(name = "Land", "land", isSelected = true, isDisabled = true) { }
                Options(name = "Condo", "condo", isSelected = true, isDisabled = false) { }
                Options(name = "Apartment", "apartment", isSelected = false, isDisabled = true) { }
                Options(name = "Boat House", "boat", isSelected = false, isDisabled = false) { }
            }
        }
    }
}