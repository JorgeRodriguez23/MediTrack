
package com.jorge.meditrack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        MedicamentoEntity::class,
        TomaMedicamentoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MediTrackDatabase : RoomDatabase() {

    abstract fun medicamentoDao(): MedicamentoDao

    abstract fun tomaMedicamentoDao(): TomaMedicamentoDao

    companion object {

        @Volatile
        private var INSTANCE: MediTrackDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS historial_tomas (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombreMedicamento TEXT NOT NULL,
                        dosis TEXT NOT NULL,
                        unidad TEXT NOT NULL,
                        horaProgramada TEXT NOT NULL,
                        fechaHoraRegistro INTEGER NOT NULL,
                        estado TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun obtenerInstancia(context: Context): MediTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MediTrackDatabase::class.java,
                    "meditrack_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
