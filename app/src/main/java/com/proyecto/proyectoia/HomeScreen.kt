package com.proyecto.proyectoia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import okhttp3.*
import java.io.IOException

@Composable
fun HomeScreen() {
    var studyTime by remember { mutableStateOf("1") }
    var failures by remember { mutableStateOf("0") }
    var wantsPostgrad by remember { mutableStateOf("Sí") }
    var grade1 by remember { mutableStateOf(3f) }
    var grade2 by remember { mutableStateOf(3f) }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Tiempo de Estudio (1-4)")
        OutlinedTextField(value = studyTime, onValueChange = { studyTime = it })

        Text("Número de Inasistencias (0-3)")
        OutlinedTextField(value = failures, onValueChange = { failures = it })

        Text("¿Interés en posgrado? (Sí/No)")
        OutlinedTextField(value = wantsPostgrad, onValueChange = { wantsPostgrad = it })

        Text("Nota 1er corte: ${grade1.toInt()}")
        Slider(value = grade1, onValueChange = { grade1 = it }, valueRange = 0f..5f)

        Text("Nota 2do corte: ${grade2.toInt()}")
        Slider(value = grade2, onValueChange = { grade2 = it }, valueRange = 0f..5f)

        Button(onClick = {
            sendPredictionRequest(
                studyTime.toIntOrNull() ?: 1,
                failures.toIntOrNull() ?: 0,
                wantsPostgrad == "Sí",
                grade1,
                grade2
            ) { prediction ->
                result = prediction
            }
        }) {
            Text("Predecir")
        }

        result?.let {
            Spacer(Modifier.height(16.dp))
            Text(
                text = if (it == "1") "❌ Si vas a abandonar tu carrera" else "✅ Si vas a continuar con tu carrera",
                color = if (it == "1") Color.Red else Color.Blue,
                fontSize = 18.sp
            )
        }
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

