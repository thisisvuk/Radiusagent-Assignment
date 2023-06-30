package com.example.assignmentradiusagent.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facilities")
data class FacilityEntity(
    @PrimaryKey val facility_id: String,
    val name: String
)