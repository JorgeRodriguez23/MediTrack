package com.jorge.meditrack

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TomaMedicamentoDao {

    @Query(
        "SELECT * FROM historial_tomas " +
                "ORDER BY fechaHoraRegistro DESC"
    )
    fun obtenerHistorial(): Flow<List<TomaMedicamentoEntity>>

    @Insert
    suspend fun insertarToma(
        toma: TomaMedicamentoEntity
    )

    @Query("DELETE FROM historial_tomas WHERE id = :id")
    suspend fun eliminarToma(id: Int)
}
