
package com.jorge.meditrack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class RecordatorioReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val idMedicamento = intent.getIntExtra(
            "idMedicamento", 1
        )

        val nombre = intent.getStringExtra("nombreMedicamento")
            ?: "tu medicamento"

        val dosis = intent.getStringExtra("dosis") ?: ""
        val unidad = intent.getStringExtra("unidad") ?: ""

        val frecuencia = intent.getStringExtra("frecuencia")
            ?: "Cada 24 horas"

        val fechaHoraAlarma = intent.getLongExtra(
            "fechaHoraAlarma", 0L
        )

        val canalId = "meditrack_recordatorios"

        val administrador = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Recordatorios de medicamentos",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Avisos de las tomas programadas"
            }

            administrador.createNotificationChannel(canal)
        }

        val notificacion = NotificationCompat.Builder(
            context, canalId
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Hora de tu medicamento")
            .setContentText(
                "Recuerda tomar $nombre. Dosis: $dosis $unidad."
            )
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Es hora de tu toma programada de $nombre. " +
                            "Dosis registrada: $dosis $unidad."
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(
                idMedicamento,
                notificacion
            )
        } catch (_: SecurityException) {
            // Las notificaciones pueden estar desactivadas.
        }

        if (fechaHoraAlarma > 0L) {
            RecordatorioScheduler.programarSiguienteRecordatorio(
                context = context,
                idMedicamento = idMedicamento,
                nombre = nombre,
                dosis = dosis,
                unidad = unidad,
                frecuencia = frecuencia,
                ultimaAlarma = fechaHoraAlarma
            )
        }
    }
}
