
package com.example.healthmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onSignIn: () -> Unit
) {
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
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 65.dp, y = 100.dp)
                .size(190.dp)
                .background(
                    Color(0xFF8CCFFF).copy(alpha = 0.18f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Surface(
                shape = RoundedCornerShape(35.dp),
                color = Color.White,
                shadowElevation = 10.dp
            ) {
                Image(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = "HealthMate Logo",
                    modifier = Modifier
                        .size(145.dp)
                        .padding(15.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Welcome to",
                fontSize = 22.sp,
                color = Color.DarkGray
            )

            Text(
                text = "HealthMate",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = purple
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Your Health, Your Journey",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF77718F)
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purple
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            TextButton(onClick = onSignIn) {
                Text(
                    text = "Already have an account? Sign In",
                    color = purple,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}
