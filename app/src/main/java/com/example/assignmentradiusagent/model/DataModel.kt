package com.example.assignmentradiusagent.model

import com.example.assignmentradiusagent.model.database.ExclusionEntity
import com.example.assignmentradiusagent.model.database.FacilityEntity
import com.example.assignmentradiusagent.model.database.OptionEntity

interface DataModel {
    suspend fun getFacilities(): List<FacilityEntity>
    suspend fun getOptionsByFacility(facilityId: String): List<OptionEntity>
    suspend fun getExclusions(): List<ExclusionEntity>
}