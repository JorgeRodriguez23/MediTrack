package com.jorge.meditrack

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorge.meditrack.ui.theme.MediTrackTheme
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MediTrackTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {

    var mostrarFormulario by remember { mutableStateOf(false) }

    if (mostrarFormulario) {
        PantallaAgregarMedicamento(
            onVolver = {
                mostrarFormulario = false
            }
        )
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "MediTrack",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Control de medicamentos"
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Próxima toma"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "💊 No hay medicamentos registrados todavía"
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    mostrarFormulario = true
                }
            ) {
                Text("Agregar medicamento")
            }
        }
    }
}



@Composable
fun PantallaAgregarMedicamento(
    onVolver: () -> Unit
) {

    val context = androidx.compose.ui.platform.LocalContext.current

    // Campos del formulario
    var nombre by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("Pastilla") }
    var hora by remember { mutableStateOf("") }
    var frecuencia by remember { mutableStateOf("Cada 24 horas") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFinalizacion by remember { mutableStateOf("") }

    // Fechas en milisegundos para controlar que la fecha final
    // no sea anterior a la fecha de inicio
    var fechaInicioMillis by remember { mutableStateOf<Long?>(null) }

    // Opciones para unidad
    val unidades = listOf(
        "Pastilla",
        "Cápsula",
        "Mililitro",
        "Gota"
    )

    // Opciones para frecuencia
    val frecuencias = listOf(
        "Cada 24 horas",
        "Cada 12 horas",
        "Cada 8 horas",
        "Cada 6 horas",
        "Una vez al día",
        "Días alternos"
    )

    var unidadExpandida by remember { mutableStateOf(false) }
    var frecuenciaExpandida by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    )  {

        // BOTÓN VOLVER

        Text(
            text = "← Agregar medicamento",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.clickable {
                onVolver()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))


        // NOMBRE

        Text(
            text = "Nombre",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            placeholder = {
                Text("Ej. Paracetamol")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        // DOSIS

        Text(
            text = "Dosis",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {

            OutlinedTextField(
                value = dosis,
                onValueChange = {
                    dosis = it
                },
                placeholder = {
                    Text("Ej. 1")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // SELECTOR DE UNIDAD

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = unidad,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Unidad")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable {
                            unidadExpandida = true
                        }
                )

                DropdownMenu(
                    expanded = unidadExpandida,
                    onDismissRequest = {
                        unidadExpandida = false
                    }
                ) {

                    unidades.forEach { opcion ->

                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },
                            onClick = {
                                unidad = opcion
                                unidadExpandida = false
                            }
                        )
                    }
                }
            }
        } // ← Cierra el Column de Dosis

        Spacer(modifier = Modifier.height(16.dp))


        // HORA

        Text(
            text = "Hora",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = hora,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text("Seleccionar hora")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Área transparente encima del campo
            // para abrir el selector de hora
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {

                        val calendario = Calendar.getInstance()

                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->

                                val amPm =
                                    if (hourOfDay >= 12) "PM" else "AM"

                                val hora12 =
                                    when {
                                        hourOfDay == 0 -> 12
                                        hourOfDay > 12 -> hourOfDay - 12
                                        else -> hourOfDay
                                    }

                                hora = String.format(
                                    Locale.getDefault(),
                                    "%02d:%02d %s",
                                    hora12,
                                    minute,
                                    amPm
                                )
                            },
                            calendario.get(Calendar.HOUR_OF_DAY),
                            calendario.get(Calendar.MINUTE),
                            false
                        ).show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        // FRECUENCIA

            // FRECUENCIA

            Text(
                text = "Frecuencia",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = frecuencia,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable {
                            frecuenciaExpandida = true
                        }
                )

                DropdownMenu(
                    expanded = frecuenciaExpandida,
                    onDismissRequest = {
                        frecuenciaExpandida = false
                    }
                ) {

                    frecuencias.forEach { opcion ->

                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },
                            onClick = {
                                frecuencia = opcion
                                frecuenciaExpandida = false
                            }
                        )
                    }
                }
            }

        Spacer(modifier = Modifier.height(16.dp))


        // FECHA DE INICIO

        Text(
            text = "Fecha de inicio",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text("Seleccionar fecha")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {

                        val calendario = Calendar.getInstance()

                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->

                                fechaInicio = String.format(
                                    Locale.getDefault(),
                                    "%02d / %02d / %04d",
                                    dayOfMonth,
                                    month + 1,
                                    year
                                )

                                // Guardamos la fecha seleccionada
                                val fechaSeleccionada =
                                    Calendar.getInstance()

                                fechaSeleccionada.set(
                                    year,
                                    month,
                                    dayOfMonth,
                                    0,
                                    0,
                                    0
                                )

                                fechaSeleccionada.set(
                                    Calendar.MILLISECOND,
                                    0
                                )

                                fechaInicioMillis =
                                    fechaSeleccionada.timeInMillis

                                // Si la fecha final anterior queda
                                // antes de la nueva fecha de inicio,
                                // la eliminamos.
                                fechaFinalizacion = ""
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        // FECHA DE FINALIZACIÓN

        Text(
            text = "Fecha de finalización",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = fechaFinalizacion,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text("Seleccionar fecha")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {

                        val calendario = Calendar.getInstance()

                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->

                                fechaFinalizacion = String.format(
                                    Locale.getDefault(),
                                    "%02d / %02d / %04d",
                                    dayOfMonth,
                                    month + 1,
                                    year
                                )
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                        ).apply {

                            // Si ya existe una fecha de inicio,
                            // no permitimos elegir una fecha anterior.
                            fechaInicioMillis?.let {
                                datePicker.minDate = it
                            }

                        }.show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        // BOTÓN GUARDAR

            Button(
                onClick = {

                    if (
                        nombre.isBlank() ||
                        dosis.isBlank() ||
                        hora.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Completa los campos principales",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Medicamento guardado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("GUARDAR")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }