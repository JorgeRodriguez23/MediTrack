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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jorge.meditrack.ui.theme.MediTrackTheme
import java.util.Calendar
import java.util.Locale


// ============================================================
// MODELO DE MEDICAMENTO
// ============================================================

data class Medicamento(
    val nombre: String,
    val dosis: String,
    val unidad: String,
    val hora: String,
    val frecuencia: String,
    val fechaInicio: String,
    val fechaFinalizacion: String
)


// ============================================================
// ACTIVIDAD PRINCIPAL
// ============================================================

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


// ============================================================
// PANTALLA PRINCIPAL
// ============================================================

@Composable
fun PantallaPrincipal() {

    // Lista de medicamentos registrados
    var medicamentos by remember {
        mutableStateOf(listOf<Medicamento>())
    }

    // Controla si mostramos el formulario
    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    if (mostrarFormulario) {

        PantallaAgregarMedicamento(

            // Cuando presionamos "volver"
            onVolver = {
                mostrarFormulario = false
            },

            // Cuando guardamos un medicamento
            onGuardar = { nuevoMedicamento ->

                medicamentos = medicamentos + nuevoMedicamento

                mostrarFormulario = false
            }
        )

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            // ------------------------------------------------
            // TÍTULO
            // ------------------------------------------------

            Text(
                text = "MediTrack",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Control de medicamentos",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))


            // ------------------------------------------------
            // SECCIÓN PRÓXIMA TOMA
            // ------------------------------------------------

            Text(
                text = "Próxima toma",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (medicamentos.isEmpty()) {

                Text(
                    text = "💊 No hay medicamentos registrados todavía"
                )

            } else {

                // Por ahora mostramos el primer medicamento
                // como la próxima toma.
                val proximo = medicamentos.first()

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "💊 ${proximo.nombre}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${proximo.dosis} ${proximo.unidad}"
                        )

                        Text(
                            text = "Hora: ${proximo.hora}"
                        )

                        Text(
                            text = proximo.frecuencia
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            // ------------------------------------------------
            // LISTA DE MEDICAMENTOS
            // ------------------------------------------------

            Text(
                text = "Mis medicamentos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))


            if (medicamentos.isEmpty()) {

                Text(
                    text = "Aquí aparecerán los medicamentos que agregues."
                )

            } else {

                // Lista desplazable de medicamentos
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(medicamentos) { medicamento ->

                        TarjetaMedicamento(
                            medicamento = medicamento,

                            // Eliminar medicamento
                            onEliminar = {

                                medicamentos = medicamentos.filter {
                                    it != medicamento
                                }
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


            // ------------------------------------------------
            // BOTÓN AGREGAR
            // ------------------------------------------------

            Button(
                onClick = {
                    mostrarFormulario = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar medicamento")
            }
        }
    }
}


// ============================================================
// TARJETA DE MEDICAMENTO
// ============================================================

@Composable
fun TarjetaMedicamento(
    medicamento: Medicamento,
    onEliminar: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Nombre
            Text(
                text = "💊 ${medicamento.nombre}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))


            // Dosis
            Text(
                text = "Dosis: ${medicamento.dosis} ${medicamento.unidad}"
            )

            // Hora
            Text(
                text = "Hora: ${medicamento.hora}"
            )

            // Frecuencia
            Text(
                text = "Frecuencia: ${medicamento.frecuencia}"
            )

            // Fecha de inicio
            if (medicamento.fechaInicio.isNotBlank()) {
                Text(
                    text = "Inicio: ${medicamento.fechaInicio}"
                )
            }

            // Fecha final
            if (medicamento.fechaFinalizacion.isNotBlank()) {
                Text(
                    text = "Finalización: ${medicamento.fechaFinalizacion}"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            // Botón eliminar
            OutlinedButton(
                onClick = onEliminar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar")
            }
        }
    }
}


// ============================================================
// PANTALLA AGREGAR MEDICAMENTO
// ============================================================

@Composable
fun PantallaAgregarMedicamento(
    onVolver: () -> Unit,
    onGuardar: (Medicamento) -> Unit
) {

    val context = LocalContext.current


    // --------------------------------------------------------
    // VARIABLES DEL FORMULARIO
    // --------------------------------------------------------

    var nombre by remember {
        mutableStateOf("")
    }

    var dosis by remember {
        mutableStateOf("")
    }

    var unidad by remember {
        mutableStateOf("Pastilla")
    }

    var hora by remember {
        mutableStateOf("")
    }

    var frecuencia by remember {
        mutableStateOf("Cada 24 horas")
    }

    var fechaInicio by remember {
        mutableStateOf("")
    }

    var fechaFinalizacion by remember {
        mutableStateOf("")
    }

    var fechaInicioMillis by remember {
        mutableStateOf<Long?>(null)
    }


    // --------------------------------------------------------
    // OPCIONES
    // --------------------------------------------------------

    val unidades = listOf(
        "Pastilla",
        "Cápsula",
        "Mililitro",
        "Gota"
    )

    val frecuencias = listOf(
        "Cada 24 horas",
        "Cada 12 horas",
        "Cada 8 horas",
        "Cada 6 horas",
        "Una vez al día",
        "Días alternos"
    )


    // --------------------------------------------------------
    // ESTADO DE MENÚS
    // --------------------------------------------------------

    var unidadExpandida by remember {
        mutableStateOf(false)
    }

    var frecuenciaExpandida by remember {
        mutableStateOf(false)
    }


    // --------------------------------------------------------
    // CONTENIDO
    // --------------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // ----------------------------------------------------
        // VOLVER
        // ----------------------------------------------------

        Text(
            text = "← Agregar medicamento",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.clickable {
                onVolver()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))


        // ----------------------------------------------------
        // NOMBRE
        // ----------------------------------------------------

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            placeholder = {
                Text("Ej. Paracetamol")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        // ----------------------------------------------------
        // DOSIS Y UNIDAD
        // ----------------------------------------------------

        Text(
            text = "Dosis",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            // Cantidad
            OutlinedTextField(
                value = dosis,
                onValueChange = {
                    dosis = it
                },
                placeholder = {
                    Text("1")
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))


            // Unidad
            Box(
                modifier = Modifier.weight(1f)
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
        }

        Spacer(modifier = Modifier.height(16.dp))


        // ----------------------------------------------------
        // HORA
        // ----------------------------------------------------

        Text(
            text = "Hora",
            style = MaterialTheme.typography.titleMedium
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
                    Text("08:00 PM")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {

                        val calendario = Calendar.getInstance()

                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->

                                val amPm =
                                    if (hourOfDay >= 12) "PM"
                                    else "AM"

                                val hora12 = when {
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


        // ----------------------------------------------------
        // FRECUENCIA
        // ----------------------------------------------------

        Text(
            text = "Frecuencia",
            style = MaterialTheme.typography.titleMedium
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


        // ----------------------------------------------------
        // FECHA DE INICIO
        // ----------------------------------------------------

        Text(
            text = "Fecha de inicio",
            style = MaterialTheme.typography.titleMedium
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
                    Text("26 / 08 / 2026")
                },
                modifier = Modifier.fillMaxWidth()
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

                                val fechaSeleccionada =
                                    Calendar.getInstance().apply {

                                        set(
                                            year,
                                            month,
                                            dayOfMonth,
                                            0,
                                            0,
                                            0
                                        )

                                        set(
                                            Calendar.MILLISECOND,
                                            0
                                        )
                                    }

                                fechaInicioMillis =
                                    fechaSeleccionada.timeInMillis

                                // Si cambiamos la fecha de inicio,
                                // limpiamos la fecha final.
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


        // ----------------------------------------------------
        // FECHA DE FINALIZACIÓN
        // ----------------------------------------------------

        Text(
            text = "Fecha de finalización",
            style = MaterialTheme.typography.titleMedium
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
                    Text("26 / 09 / 2026")
                },
                modifier = Modifier.fillMaxWidth()
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

                            // La fecha final no puede ser
                            // anterior a la fecha inicial.
                            fechaInicioMillis?.let {
                                datePicker.minDate = it
                            }

                        }.show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        // ----------------------------------------------------
        // GUARDAR
        // ----------------------------------------------------

        Button(
            onClick = {

                // Validamos los campos principales
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

                    // Creamos el medicamento
                    val medicamento = Medicamento(
                        nombre = nombre,
                        dosis = dosis,
                        unidad = unidad,
                        hora = hora,
                        frecuencia = frecuencia,
                        fechaInicio = fechaInicio,
                        fechaFinalizacion = fechaFinalizacion
                    )

                    // Enviamos el medicamento a la
                    // pantalla principal
                    onGuardar(medicamento)

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