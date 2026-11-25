
package com.example.healthmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    records: List<HealthRecord>,
    onAddRecord: () -> Unit,
    onViewHistory: () -> Unit
) {
    val today = SimpleDateFormat(
        "yyyy-MM-dd", Locale.getDefault()
    ).format(Date())

    val todayRecords = records.filter { it.date == today }
    val steps = todayRecords.sumOf { it.steps }
    val calories = todayRecords.sumOf { it.calories }
    val water = todayRecords.sumOf { it.water }

    val purple = Color(0xFF6C63FF)

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
                .size(190.dp)
                .background(
                    Color(0xFF8CCFFF).copy(alpha = 0.18f),
                    CircleShape
                )
        )

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-65).dp, y = (-75).dp)
                .size(200.dp)
                .background(
                    Color(0xFF70BFFF).copy(alpha = 0.13f),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
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
                    .padding(22.dp)
            ) {
                Text(
                    "Hello, Welcome!",
                    fontSize = 17.sp,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "HealthMate",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Your Daily Health Summary",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Text(
                "Today's Overview",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39334F)
            )

            SummaryCard("Steps Walked", "$steps steps", purple)
            SummaryCard("Calories Burned", "$calories kcal", purple)
            SummaryCard("Water Intake", "$water ml", purple)

            Button(
                onClick = onAddRecord,
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
                    "＋  Add Health Record",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onViewHistory,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = purple
                )
            ) {
                Text("View History", fontSize = 15.sp)
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    purple: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39334F)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = purple
            )
        }
    }
}
