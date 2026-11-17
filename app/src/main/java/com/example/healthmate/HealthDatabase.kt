
package com.example.healthmate

import android.content.Context
import androidx.room.*

@Database(entities = [HealthRecord::class], version = 1)
abstract class HealthDatabase : RoomDatabase() {

    abstract fun recordDao(): HealthRecordDao

    companion object {
        private var instance: HealthDatabase? = null

        fun getDatabase(context: Context): HealthDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "healthmate_db"
                ).build()
            }
            return instance!!
        }
    }
}
