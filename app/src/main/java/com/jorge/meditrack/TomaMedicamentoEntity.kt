package com.jorge.meditrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_tomas")
data class TomaMedicamentoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Datos del medicamento al momento de registrar la toma
    val nombreMedicamento: String,
    val dosis: String,
    val unidad: String,
    val horaProgramada: String,

    // Fecha y hora exactas del registro
    val fechaHoraRegistro: Long,

    // Estado de la toma: "Tomado" u "Omitido"
    val estado: String
)
