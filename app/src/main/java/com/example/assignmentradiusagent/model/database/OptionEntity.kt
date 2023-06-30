package com.example.assignmentradiusagent.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "options")
data class OptionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
    val facilityId: String
)