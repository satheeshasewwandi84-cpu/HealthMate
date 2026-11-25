
package com.example.healthmate

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.example.healthmate.ui.theme.HealthMateTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var db: HealthDatabase
    private var pendingAction = ""

    private val permission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            when (pendingAction) {
                "add" -> GoalNotification.recordAdded(this)
                "edit" -> GoalNotification.recordUpdated(this)
                "delete" -> GoalNotification.recordDeleted(this)
            }

            pendingAction = ""
            checkGoals()
        }
    }

    private fun requestNotifications(action: String) {
        if (
            Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            pendingAction = action
            permission.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            when (action) {
                "add" -> GoalNotification.recordAdded(this)
                "edit" -> GoalNotification.recordUpdated(this)
                "delete" -> GoalNotification.recordDeleted(this)
            }

            checkGoals()
        }
    }

    private fun checkGoals() {
        lifecycleScope.launch {
            val today = SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault()
            ).format(Date())

            GoalNotification.check(
                this@MainActivity,
                db.recordDao().getByDate(today)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = HealthDatabase.getDatabase(this)

        setContent {
            HealthMateTheme {

                var screen by remember {
                    mutableStateOf("welcome")
                }

                var editRecord by remember {
                    mutableStateOf<HealthRecord?>(null)
                }

                val records = remember {
                    mutableStateListOf<HealthRecord>()
                }

                val scope = rememberCoroutineScope()

                suspend fun refresh() {
                    records.clear()
                    records.addAll(db.recordDao().getAll())
                }

                LaunchedEffect(Unit) {
                    if (db.recordDao().getAll().isEmpty()) {
                        SampleData.records.forEach {
                            db.recordDao().insert(it)
                        }
                    }

                    refresh()
                }

                Scaffold(
                    containerColor = Color(0xFFF7F4FF)
                ) { padding ->

                    Box(Modifier.padding(padding)) {

                        when (screen) {

                            "welcome" -> WelcomeScreen(
                                onGetStarted = {
                                    screen = "home"
                                },
                                onSignIn = {}
                            )

                            "home" -> DashboardScreen(
                                records = records,
                                onAddRecord = {
                                    editRecord = null
                                    screen = "add"
                                },
                                onViewHistory = {
                                    screen = "history"
                                }
                            )

                            "add" -> AddRecordScreen(
                                record = editRecord,
                                onBack = {
                                    editRecord = null
                                    screen = "home"
                                },
                                onSave = { record ->
                                    scope.launch {
                                        val action =
                                            if (editRecord == null) "add"
                                            else "edit"

                                        if (editRecord == null) {
                                            db.recordDao().insert(record)
                                        } else {
                                            db.recordDao().update(record)
                                        }

                                        refresh()
                                        editRecord = null
                                        screen = "history"

                                        requestNotifications(action)
                                    }
                                }
                            )

                            "history" -> HistoryScreen(
                                records = records,
                                onBack = {
                                    screen = "home"
                                },
                                onEdit = {
                                    editRecord = it
                                    screen = "add"
                                },
                                onDelete = {
                                    scope.launch {
                                        db.recordDao().delete(it)
                                        refresh()
                                        requestNotifications("delete")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
