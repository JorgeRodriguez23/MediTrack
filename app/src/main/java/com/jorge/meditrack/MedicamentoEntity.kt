package com.jorge.meditrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicamentos")
data class MedicamentoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val dosis: String,
    val unidad: String,
    val hora: String,
    val frecuencia: String,
    val fechaInicio: String,
    val fechaFinalizacion: String
)

