package com.example.healthmate

import androidx.room.*

@Dao
interface HealthRecordDao {

    @Insert
    suspend fun insert(record: HealthRecord)

    @Update
    suspend fun update(record: HealthRecord)

    @Delete
    suspend fun delete(record: HealthRecord)

    @Query("SELECT * FROM health_records ORDER BY id DESC")
    suspend fun getAll(): List<HealthRecord>

    @Query("SELECT * FROM health_records WHERE date = :date")
    suspend fun getByDate(date: String): List<HealthRecord>
}