package com.example.assignmentradiusagent.model.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

// Defining Room Database for offline storage of API Results
@Database(entities = [FacilityEntity::class, OptionEntity::class, ExclusionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Abstract methods for accessing Dao
    abstract fun facilityDao(): FacilityDao
    abstract fun optionDao(): OptionDao
    abstract fun exclusionDao(): ExclusionDao

    // Creating a singleton instance so that there will be only one instance of DB in whole app
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

// Defining DAO along with functions to retrieve and insert the data
@Dao
interface FacilityDao {
    @Query("SELECT * FROM facilities")
    suspend fun getAllFacilities(): List<FacilityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacilities(facilities: List<FacilityEntity>)
}

@Dao
interface OptionDao {
    @Query("SELECT * FROM options WHERE facilityId = :facilityId")
    suspend fun getOptionsByFacility(facilityId: String): List<OptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<OptionEntity>)
}

@Dao
interface ExclusionDao {
    @Query("SELECT * FROM exclusions")
    suspend fun getExclusions(): List<ExclusionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExclusions(exclusions:List<ExclusionEntity>)
}
