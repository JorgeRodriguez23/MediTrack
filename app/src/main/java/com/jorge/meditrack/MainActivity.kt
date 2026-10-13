
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jorge.meditrack.ui.theme.MediTrackTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

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
    val fechaFinalizacion: String,
    val id: Int = 0
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
                MediTrackApp()
            }
        }
    }
}

// ============================================================
// CONTROL GENERAL DE LA APLICACIÓN
// ============================================================

@Composable
fun MediTrackApp() {

    val context = LocalContext.current

    val database = remember {
        MediTrackDatabase.obtenerInstancia(context)
    }

    val medicamentoDao = remember {
        database.medicamentoDao()
    }

    val tomaDao = remember {
        database.tomaMedicamentoDao()
    }

    val medicamentosGuardados by medicamentoDao
        .obtenerTodos()
        .collectAsState(initial = emptyList())

    val historial by tomaDao
        .obtenerHistorial()
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    val medicamentos = medicamentosGuardados.map { registro ->
        Medicamento(
            id = registro.id,
            nombre = registro.nombre,
            dosis = registro.dosis,
            unidad = registro.unidad,
            hora = registro.hora,
            frecuencia = registro.frecuencia,
            fechaInicio = registro.fechaInicio,
            fechaFinalizacion = registro.fechaFinalizacion
        )
    }

    var pantallaActual by remember {
        mutableStateOf("inicio")
    }

    when (pantallaActual) {

        "inicio" -> {
            PantallaPrincipal(
                medicamentos = medicamentos,
                onAgregar = {
                    pantallaActual = "agregar"
                },
                onVerMedicamentos = {
                    pantallaActual = "lista"
                },
                onVerHistorial = {
                    pantallaActual = "historial"
                }
            )
        }

        "lista" -> {
            PantallaListaMedicamentos(
                medicamentos = medicamentos,
                onVolver = {
                    pantallaActual = "inicio"
                },
                onAgregar = {
                    pantallaActual = "agregar"
                },
                onEliminar = { medicamento ->
                    scope.launch {
                        medicamentoDao.eliminar(
                            MedicamentoEntity(
                                id = medicamento.id,
                                nombre = medicamento.nombre,
                                dosis = medicamento.dosis,
                                unidad = medicamento.unidad,
                                hora = medicamento.hora,
                                frecuencia = medicamento.frecuencia,
                                fechaInicio = medicamento.fechaInicio,
                                fechaFinalizacion =
                                    medicamento.fechaFinalizacion
                            )
                        )
                    }
                },
                onRegistrarToma = { medicamento, estado ->
                    scope.launch {
                        tomaDao.insertarToma(
                            TomaMedicamentoEntity(
                                nombreMedicamento = medicamento.nombre,
                                dosis = medicamento.dosis,
                                unidad = medicamento.unidad,
                                horaProgramada = medicamento.hora,
                                fechaHoraRegistro =
                                    System.currentTimeMillis(),
                                estado = estado
                            )
                        )
                    }

                    Toast.makeText(
                        context,
                        "Registro guardado: $estado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        "agregar" -> {
            PantallaAgregarMedicamento(
                onVolver = {
                    pantallaActual = "inicio"
                },
                onGuardar = { nuevoMedicamento ->
                    scope.launch {
                        medicamentoDao.insertar(
                            MedicamentoEntity(
                                nombre = nuevoMedicamento.nombre,
                                dosis = nuevoMedicamento.dosis,
                                unidad = nuevoMedicamento.unidad,
                                hora = nuevoMedicamento.hora,
                                frecuencia = nuevoMedicamento.frecuencia,
                                fechaInicio = nuevoMedicamento.fechaInicio,
                                fechaFinalizacion =
                                    nuevoMedicamento.fechaFinalizacion
                            )
                        )

                        pantallaActual = "lista"
                    }
                }
            )
        }

        "historial" -> {
            PantallaHistorialTomas(
                historial = historial,
                onVolver = {
                    pantallaActual = "inicio"
                },
                onEliminarRegistro = { registro ->
                    scope.launch {
                        tomaDao.eliminarToma(registro.id)
                    }
                }
            )
        }
    }
}

// ============================================================
// PANTALLA PRINCIPAL
// ============================================================

@Composable
fun PantallaPrincipal(
    medicamentos: List<Medicamento>,
    onAgregar: () -> Unit,
    onVerMedicamentos: () -> Unit,
    onVerHistorial: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "MediTrack",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Control de medicamentos",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Próxima toma",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (medicamentos.isEmpty()) {
            Text("💊 No hay medicamentos registrados todavía")
        } else {
            val proximo = medicamentos.first()

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💊 ${proximo.nombre}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("${proximo.dosis} ${proximo.unidad}")
                    Text("Hora: ${proximo.hora}")
                    Text(proximo.frecuencia)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onVerMedicamentos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver mis medicamentos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAgregar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar medicamento")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onVerHistorial,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Historial de tomas")
        }
    }
}

// ============================================================
// PANTALLA LISTA DE MEDICAMENTOS
// ============================================================

@Composable
fun PantallaListaMedicamentos(
    medicamentos: List<Medicamento>,
    onVolver: () -> Unit,
    onAgregar: () -> Unit,
    onEliminar: (Medicamento) -> Unit,
    onRegistrarToma: (Medicamento, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "← Mis medicamentos",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.clickable {
                onVolver()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Lista de medicamentos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (medicamentos.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("💊")
                Spacer(modifier = Modifier.height(8.dp))
                Text("No hay medicamentos registrados.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medicamentos) { medicamento ->
                    TarjetaMedicamento(
                        medicamento = medicamento,
                        onEliminar = {
                            onEliminar(medicamento)
                        },
                        onTomado = {
                            onRegistrarToma(medicamento, "Tomado")
                        },
                        onOmitido = {
                            onRegistrarToma(medicamento, "Omitido")
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAgregar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar medicamento")
        }
    }
}

// ============================================================
// TARJETA DE MEDICAMENTO
// ============================================================

@Composable
fun TarjetaMedicamento(
    medicamento: Medicamento,
    onEliminar: () -> Unit,
    onTomado: () -> Unit,
    onOmitido: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "💊 ${medicamento.nombre}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Dosis: ${medicamento.dosis} ${medicamento.unidad}")
            Text("Hora: ${medicamento.hora}")
            Text("Frecuencia: ${medicamento.frecuencia}")

            if (medicamento.fechaInicio.isNotBlank()) {
                Text("Inicio: ${medicamento.fechaInicio}")
            }

            if (medicamento.fechaFinalizacion.isNotBlank()) {
                Text("Finalización: ${medicamento.fechaFinalizacion}")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onTomado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomado")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onOmitido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Omitido")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onEliminar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar medicamento")
            }
        }
    }
}

// ============================================================
// PANTALLA HISTORIAL DE TOMAS
// ============================================================

@Composable
fun PantallaHistorialTomas(
    historial: List<TomaMedicamentoEntity>,
    onVolver: () -> Unit,
    onEliminarRegistro: (TomaMedicamentoEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "← Historial de tomas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.clickable {
                onVolver()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Registros anteriores",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (historial.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📋")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Todavía no hay tomas registradas.")
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Registra una toma desde la lista de medicamentos.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historial, key = { it.id }) { registro ->
                    TarjetaHistorial(
                        registro = registro,
                        onEliminar = {
                            onEliminarRegistro(registro)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}

// ============================================================
// TARJETA DE REGISTRO DEL HISTORIAL
// ============================================================

@Composable
fun TarjetaHistorial(
    registro: TomaMedicamentoEntity,
    onEliminar: () -> Unit
) {
    val formato = remember {
        SimpleDateFormat("dd/MM/yyyy 'a las' hh:mm a", Locale.getDefault())
    }

    val fechaFormateada = remember(registro.fechaHoraRegistro) {
        formato.format(Date(registro.fechaHoraRegistro))
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "💊 ${registro.nombreMedicamento}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Dosis: ${registro.dosis} ${registro.unidad}")
            Text("Hora programada: ${registro.horaProgramada}")
            Text("Fecha del registro: $fechaFormateada")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Estado: ${registro.estado}",
                style = MaterialTheme.typography.titleSmall,
                color = if (registro.estado == "Tomado") {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onEliminar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar registro")
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

    var nombre by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("Pastilla") }
    var hora by remember { mutableStateOf("") }
    var frecuencia by remember { mutableStateOf("Cada 24 horas") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFinalizacion by remember { mutableStateOf("") }
    var fechaInicioMillis by remember { mutableStateOf<Long?>(null) }

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
        "Días alternos"
    )

    var unidadExpandida by remember { mutableStateOf(false) }
    var frecuenciaExpandida by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = "← Agregar medicamento",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.clickable {
                onVolver()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            placeholder = { Text("Ej. Paracetamol") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Dosis", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = dosis,
                onValueChange = { dosis = it },
                placeholder = { Text("1") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {

                OutlinedTextField(
                    value = unidad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unidad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { unidadExpandida = true }
                )

                DropdownMenu(
                    expanded = unidadExpandida,
                    onDismissRequest = { unidadExpandida = false }
                ) {
                    unidades.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
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

        Text("Hora", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = hora,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("08:00 PM") },
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
                                    if (hourOfDay >= 12) "PM" else "AM"

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

        Text("Frecuencia", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = frecuencia,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { frecuenciaExpandida = true }
            )

            DropdownMenu(
                expanded = frecuenciaExpandida,
                onDismissRequest = { frecuenciaExpandida = false }
            ) {
                frecuencias.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            frecuencia = opcion
                            frecuenciaExpandida = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Fecha de inicio", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("26 / 08 / 2026") },
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

                                val seleccionada =
                                    Calendar.getInstance().apply {
                                        set(
                                            year,
                                            month,
                                            dayOfMonth,
                                            0,
                                            0,
                                            0
                                        )
                                        set(Calendar.MILLISECOND, 0)
                                    }

                                fechaInicioMillis = seleccionada.timeInMillis
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

        Text(
            "Fecha de finalización",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = fechaFinalizacion,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("26 / 09 / 2026") },
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
                            fechaInicioMillis?.let {
                                datePicker.minDate = it
                            }
                        }.show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

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
                    val medicamento = Medicamento(
                        nombre = nombre,
                        dosis = dosis,
                        unidad = unidad,
                        hora = hora,
                        frecuencia = frecuencia,
                        fechaInicio = fechaInicio,
                        fechaFinalizacion = fechaFinalizacion
                    )

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
