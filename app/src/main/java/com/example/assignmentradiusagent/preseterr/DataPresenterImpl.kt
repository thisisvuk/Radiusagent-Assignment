package com.example.assignmentradiusagent.preseterr

import android.util.Log
import com.example.assignmentradiusagent.model.DataModel
import com.example.assignmentradiusagent.model.dataclass.Facilities
import com.example.assignmentradiusagent.model.database.OptionEntity
import com.example.assignmentradiusagent.view.DataView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataPresenterImpl(private val dataModel: DataModel, private val view: DataView) : DataPresenter {

    // Implementing the DataPresenter Interface

    override fun getFacilities() {
        view.showLoading()

        // Starting coroutine using GlobalScope to perform the asynchronous operations
        GlobalScope.launch {
            try {
                val facilityList = arrayListOf<Facilities>()

                // Here it will get facilities from dataModel
                val facilities = dataModel.getFacilities()

                // Here it will get options for each facility from dataModel
                facilities.forEach { facilityEntity ->
                    val Options = dataModel.getOptionsByFacility(facilityEntity.facility_id)
                    val options = Options.map { option ->
                        OptionEntity(option.id, option.name, option.icon, option.facilityId)
                    }
                    facilityList.add(Facilities(facilityEntity.facility_id, facilityEntity.name, options))
                }

                // Here exclusions will be fetched and added to mutable map
                val exclusions = dataModel.getExclusions()
                val exclusionMap = mutableMapOf<String, String>()

                exclusions.forEach {
                    exclusionMap[it.pair1] = it.pair2
                }
                // Switching to main dispatcher to update the UI on the main thread
                withContext(Dispatchers.Main) {
                    // Displaying the facilities and exclusionMap on the view
                    view.displayFacilities(facilityList, exclusionMap)
                    view.hideLoading()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showError("Error fetching facilities: ${e.message}")
                    view.hideLoading()
                }
            }
        }
    }

    override fun onDestroy() {
    }
}