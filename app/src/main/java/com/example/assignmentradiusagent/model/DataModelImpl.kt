package com.example.assignmentradiusagent.model

import com.example.assignmentradiusagent.model.database.AppDatabase
import com.example.assignmentradiusagent.model.database.ExclusionEntity
import com.example.assignmentradiusagent.model.database.FacilityEntity
import com.example.assignmentradiusagent.model.database.OptionEntity
import com.example.assignmentradiusagent.model.dataclass.Exclusion
import com.example.assignmentradiusagent.model.dataclass.Exclusions

class DataModelImpl(private val apiService: ApiService, private val db: AppDatabase) : DataModel {

    // Implementing the DataModel Interface

    override suspend fun getFacilities(): List<FacilityEntity> {
        val facilitiesFromDb = db.facilityDao().getAllFacilities()

        // If Database is empty then it will fetch data from the API and insert it into the database
        return facilitiesFromDb.ifEmpty {
            val response = apiService.getApiData()
            val facilities = response.facilities.map { FacilityEntity(it.facility_id, it.name) }
            response.facilities.forEach { facility ->
                val options = facility.options.map { OptionEntity(it.id, it.name, it.icon, facility.facility_id) }
                db.optionDao().insertOptions(options)
            }
            val exclusions = response.exclusions.map {
                ExclusionEntity(it[0].facility_id + "," + it[0].options_id, it[1].facility_id + "," + it[1].options_id)
            }
            db.facilityDao().insertFacilities(facilities)
            db.exclusionDao().insertExclusions(exclusions)
            facilities
        }
    }

    // Retrieving the Options from the Database
    override suspend fun getOptionsByFacility(facilityId: String): List<OptionEntity> {
        return db.optionDao().getOptionsByFacility(facilityId)
    }

    // Retrieving the Exclusions from the Database
    override suspend fun getExclusions(): List<ExclusionEntity> {
        return db.exclusionDao().getExclusions()
    }


}