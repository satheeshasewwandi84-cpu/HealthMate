
package com.example.healthmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    onBack: () -> Unit,
    onSave: (HealthRecord) -> Unit,
    record: HealthRecord? = null
) {
    var steps by remember(record) {
        mutableStateOf(record?.steps?.toString() ?: "")
    }
    var calories by remember(record) {
        mutableStateOf(record?.calories?.toString() ?: "")
    }
    var water by remember(record) {
        mutableStateOf(record?.water?.toString() ?: "")
    }

    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var date by remember(record) {
        mutableLongStateOf(
            record?.let {
                format.parse(it.date)?.time
            } ?: System.currentTimeMillis()
        )
    }

    var showDate by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    val purple = Color(0xFF6C63FF)
    val dateText = format.format(Date(date))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFD9F0FF),
                        Color(0xFFEAF6FF),
                        Color(0xFFF8FCFF)
                    )
                )
            )
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = 65.dp, y = 100.dp)
                .size(170.dp)
                .background(
                    Color(0xFF8CCFFF).copy(alpha = 0.16f),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF6C63FF),
                                Color(0xFF938BFF)
                            )
                        ),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                Text(
                    if (record == null) "Add Health Record"
                    else "Edit Health Record",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Keep track of your daily health activities",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }

            Text(
                "Record Date",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39334F)
            )

            OutlinedButton(
                onClick = { showDate = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(dateText, color = purple)
            }

            if (showDate) {
                val picker = rememberDatePickerState(
                    initialSelectedDateMillis = date
                )

                DatePickerDialog(
                    onDismissRequest = { showDate = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                picker.selectedDateMillis?.let {
                                    date = it
                                }
                                showDate = false
                            }
                        ) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = picker)
                }
            }

            Text(
                "Daily Activities",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39334F)
            )

            OutlinedTextField(
                value = steps,
                onValueChange = { steps = it },
                label = { Text("Steps Walked") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text("Calories Burned (kcal)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = water,
                onValueChange = { water = it },
                label = { Text("Water Intake (ml)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            if (error) {
                Text(
                    "Please enter valid numbers.",
                    color = Color.Red
                )
            }

            Button(
                onClick = {
                    val s = steps.toIntOrNull()
                    val c = calories.toIntOrNull()
                    val w = water.toIntOrNull()

                    error = s == null || c == null || w == null ||
                            s < 0 || c < 0 || w < 0

                    if (!error && s != null && c != null && w != null) {
                        onSave(
                            HealthRecord(
                                id = record?.id ?: 0,
                                date = dateText,
                                steps = s,
                                calories = c,
                                water = w
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    if (record == null) "Save Record"
                    else "Update Record",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = purple
                )
            ) {
                Text("Back", fontSize = 15.sp)
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
