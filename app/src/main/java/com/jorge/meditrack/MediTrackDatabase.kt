
package com.jorge.meditrack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MedicamentoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MediTrackDatabase : RoomDatabase() {

    abstract fun medicamentoDao(): MedicamentoDao

    companion object {
        @Volatile
        private var INSTANCE: MediTrackDatabase? = null

        fun obtenerInstancia(context: Context): MediTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MediTrackDatabase::class.java,
                    "meditrack_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}


