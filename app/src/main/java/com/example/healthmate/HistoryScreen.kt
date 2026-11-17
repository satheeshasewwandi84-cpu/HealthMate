
package com.example.healthmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen(
    records: List<HealthRecord>,
    onBack: () -> Unit,
    onDelete: (HealthRecord) -> Unit,
    onEdit: (HealthRecord) -> Unit
) {
    var search by remember { mutableStateOf("") }

    val result = records.filter {
        it.date.contains(search.trim(), ignoreCase = true)
    }

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
                .offset(x = 65.dp, y = 90.dp)
                .size(170.dp)
                .background(
                    Color(0xFF8CCFFF).copy(alpha = 0.16f),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
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
                    "Health Record History",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "View and manage your daily records",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(18.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search by Date") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(14.dp))

            Text(
                "Your Records (${result.size})",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF39334F)
            )

            Spacer(Modifier.height(10.dp))

            if (result.isEmpty()) {
                Text(
                    "No records found.",
                    color = Color(0xFF77718F)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(result, key = { it.id }) { record ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                        ) {
                            Column(
                                Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "📅  ${record.date}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = purple
                                )

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    "👣  Steps Walked: ${record.steps}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF39334F)
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    "🔥  Calories Burned: ${record.calories} kcal",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF39334F)
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    "💧  Water Intake: ${record.water} ml",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF39334F)
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = Color(0xFFE6EFFF)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(
                                        onClick = { onEdit(record) }
                                    ) {
                                        Text(
                                            "Edit",
                                            color = purple
                                        )
                                    }

                                    TextButton(
                                        onClick = { onDelete(record) }
                                    ) {
                                        Text(
                                            "Delete",
                                            color = Color(0xFFE53955)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple
                )
            ) {
                Text(
                    "Back",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
