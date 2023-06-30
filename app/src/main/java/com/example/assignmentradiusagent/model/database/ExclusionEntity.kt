package com.example.assignmentradiusagent.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exclusions")
data class ExclusionEntity(
    @PrimaryKey val pair1: String,
    val pair2: String
)