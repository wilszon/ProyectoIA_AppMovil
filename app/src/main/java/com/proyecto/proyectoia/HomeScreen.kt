package com.proyecto.proyectoia

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import okhttp3.*
import java.io.IOException
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

    // Menús desplegables
    val studyOptions = listOf("1", "2", "3", "4")
    var selectedStudyOption by remember { mutableStateOf(studyOptions[0]) }
    var expandedStudy by remember { mutableStateOf(false) }

    val failuresOptions = listOf("0", "1", "2", "3")
    var selectedFailuresOption by remember { mutableStateOf(failuresOptions[0]) }
    var expandedFailures by remember { mutableStateOf(false) }

    val postgradOptions = listOf("Sí", "No")
    var selectedPostgrad by remember { mutableStateOf(postgradOptions[0]) }
    var expandedPostgrad by remember { mutableStateOf(false) }

    var grade1 by remember { mutableStateOf(3f) }
    var grade2 by remember { mutableStateOf(3f) }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp, top = 60.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\uD83C\uDF93 Predicción de Deserción  Universitaria",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Ingresa tus datos académicos y personales clave para estimar la probabilidad de continuar o abandonar tu carrera...",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Datos del Estudiante",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(Modifier.height(20.dp))

        // Tiempo de estudio dropdown
        Text("Tiempo de Estudio (1-4)")
        ExposedDropdownMenuBox(
            expanded = expandedStudy,
            onExpandedChange = { expandedStudy = !expandedStudy }
        ) {
            TextField(
                readOnly = true,
                value = selectedStudyOption,
                onValueChange = {},
                label = { Text("Seleccione") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStudy) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedStudy,
                onDismissRequest = { expandedStudy = false }
            ) {
                studyOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedStudyOption = option
                            expandedStudy = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Inasistencias dropdown
        Text("Número de Inasistencias (0-3)")
        ExposedDropdownMenuBox(
            expanded = expandedFailures,
            onExpandedChange = { expandedFailures = !expandedFailures }
        ) {
            TextField(
                readOnly = true,
                value = selectedFailuresOption,
                onValueChange = {},
                label = { Text("Seleccione") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFailures) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedFailures,
                onDismissRequest = { expandedFailures = false }
            ) {
                failuresOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedFailuresOption = option
                            expandedFailures = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Posgrado dropdown
        Text("¿Interés en posgrado?")
        ExposedDropdownMenuBox(
            expanded = expandedPostgrad,
            onExpandedChange = { expandedPostgrad = !expandedPostgrad }
        ) {
            TextField(
                readOnly = true,
                value = selectedPostgrad,
                onValueChange = {},
                label = { Text("Seleccione") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPostgrad) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedPostgrad,
                onDismissRequest = { expandedPostgrad = false }
            ) {
                postgradOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedPostgrad = option
                            expandedPostgrad = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Nota 1er corte: ${grade1.toInt()}")
        Slider(
            value = grade1,
            onValueChange = { grade1 = it.roundToInt().toFloat() },
            valueRange = 0f..5f,
            steps = 4,
            colors = SliderDefaults.colors(
                thumbColor = Color.Blue,
                activeTrackColor = Color.Blue
            )
        )

        Spacer(Modifier.height(12.dp))

        Text("Nota 2do corte: ${grade2.toInt()}")
        Slider(
            value = grade2,
            onValueChange = { grade2 = it.roundToInt().toFloat() },
            valueRange = 0f..5f,
            steps = 4,
            colors = SliderDefaults.colors(
                thumbColor = Color.Blue,
                activeTrackColor = Color.Blue
            )
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                sendPredictionRequest(
                    studyTime = selectedStudyOption.toInt(),
                    failures = selectedFailuresOption.toInt(),
                    wantsPostgrad = selectedPostgrad == "Sí",
                    grade1 = grade1,
                    grade2 = grade2
                ) { prediction ->
                    result = prediction
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("Predecir")
        }

        Spacer(Modifier.height(20.dp))

        result?.let {
            Spacer(Modifier.height(16.dp))
            Text(
                text = if (it == "1") "❌ Si vas a abandonar tu carrera" else "✅ Si vas a continuar con tu carrera",
                color = if (it == "1") Color.Red else Color.Blue,
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Proyecto Realizado por:\nWilson Suarez\nMateo Sandoval\nCristian Cala",
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            color = Color.Black,
        )
    }
}

fun sendPredictionRequest(
    studyTime: Int,
    failures: Int,
    wantsPostgrad: Boolean,
    grade1: Float,
    grade2: Float,
    onResult: (String) -> Unit
) {
    val client = OkHttpClient()
    val json = JSONObject().apply {
        put("study_time", studyTime)
        put("number_of_failures", failures)
        put("wants_higher_education", wantsPostgrad)
        put("grade_1", grade1.toInt())
        put("grade_2", grade2.toInt())
    }

    val requestBody = json.toString().toRequestBody("application/json".toMediaType())
    val request = Request.Builder()
        .url("https://web-production-7a7f2.up.railway.app/predict")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResult("error")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            val prediction = try {
                JSONObject(body).get("prediction").toString()
            } catch (e: Exception) {
                "error"
            }
            onResult(prediction)
        }
    })
}

