package com.jorge.meditrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicamentoDao {

    @Query("SELECT * FROM medicamentos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<MedicamentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(medicamento: MedicamentoEntity)

    @Delete
    suspend fun eliminar(medicamento: MedicamentoEntity)
}


