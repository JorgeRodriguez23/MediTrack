
package com.jorge.meditrack

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object RecordatorioScheduler {

    private fun obtenerIntervalo(frecuencia: String): Long {
        val horas = when (frecuencia) {
            "Cada 12 horas" -> 12L
            "Cada 8 horas" -> 8L
            "Cada 6 horas" -> 6L
            "Días alternos" -> 48L
            else -> 24L
        }

        return horas * 60 * 60 * 1000
    }

    fun programarRecordatorio(
        context: Context,
        idMedicamento: Int,
        nombre: String,
        dosis: String,
        unidad: String,
        hora: String,
        frecuencia: String = "Cada 24 horas"
    ) {
        val formato = SimpleDateFormat(
            "hh:mm a",
            Locale.US
        ).apply {
            isLenient = false
        }

        val fechaHora: Date = try {
            formato.parse(hora.trim()) ?: return
        } catch (_: Exception) {
            return
        }

        val horaCalendario = Calendar.getInstance().apply {
            time = fechaHora
        }

        val ahora = Calendar.getInstance()

        val proximaAlarma = Calendar.getInstance().apply {
            set(
                Calendar.HOUR_OF_DAY,
                horaCalendario.get(Calendar.HOUR_OF_DAY)
            )
            set(
                Calendar.MINUTE,
                horaCalendario.get(Calendar.MINUTE)
            )
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= ahora.timeInMillis) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        programarAlarma(
            context = context,
            idMedicamento = idMedicamento,
            nombre = nombre,
            dosis = dosis,
            unidad = unidad,
            frecuencia = frecuencia,
            fechaHoraAlarma = proximaAlarma.timeInMillis
        )
    }

    fun programarSiguienteRecordatorio(
        context: Context,
        idMedicamento: Int,
        nombre: String,
        dosis: String,
        unidad: String,
        frecuencia: String,
        ultimaAlarma: Long
    ) {
        val intervalo = obtenerIntervalo(frecuencia)
        val ahora = System.currentTimeMillis()

        var siguienteAlarma = ultimaAlarma + intervalo

        // Evita programar varias notificaciones atrasadas seguidas.
        while (siguienteAlarma <= ahora) {
            siguienteAlarma += intervalo
        }

        programarAlarma(
            context = context,
            idMedicamento = idMedicamento,
            nombre = nombre,
            dosis = dosis,
            unidad = unidad,
            frecuencia = frecuencia,
            fechaHoraAlarma = siguienteAlarma
        )
    }

    private fun programarAlarma(
        context: Context,
        idMedicamento: Int,
        nombre: String,
        dosis: String,
        unidad: String,
        frecuencia: String,
        fechaHoraAlarma: Long
    ) {
        val intent = Intent(
            context,
            RecordatorioReceiver::class.java
        ).apply {
            putExtra("idMedicamento", idMedicamento)
            putExtra("nombreMedicamento", nombre)
            putExtra("dosis", dosis)
            putExtra("unidad", unidad)
            putExtra("frecuencia", frecuencia)
            putExtra("fechaHoraAlarma", fechaHoraAlarma)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            idMedicamento,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            fechaHoraAlarma,
            pendingIntent
        )
    }

    fun cancelarRecordatorio(
        context: Context,
        idMedicamento: Int
    ) {
        val intent = Intent(
            context,
            RecordatorioReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            idMedicamento,
            intent,
            PendingIntent.FLAG_NO_CREATE or
                    PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent != null) {
            val alarmManager = context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }
}
