
package com.example.healthmate

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.concurrent.atomic.AtomicInteger

object GoalNotification {

    private val notificationId = AtomicInteger(200)

    private fun show(context: Context, title: String, message: String) {
        if (Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) return

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= 26) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "health_goals",
                    "HealthMate Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        val intent = Intent(context, MainActivity::class.java)

        val pending = PendingIntent.getActivity(
            context,
            notificationId.get(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "health_goals")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pending)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(notificationId.getAndIncrement(), notification)
    }

    fun check(context: Context, records: List<HealthRecord>) {
        val steps = records.sumOf { it.steps }
        val calories = records.sumOf { it.calories }
        val water = records.sumOf { it.water }

        if (steps >= 6000) {
            show(context, "Steps Goal Completed!", "You reached 1,500 steps today.")
        }

        if (calories >= 500) {
            show(context, "Calories Goal Completed!", "You burned 100 calories today.")
        }

        if (water >= 2000) {
            show(context, "Water Goal Completed!", "You drank 1,500 ml of water today.")
        }
    }

    fun recordAdded(context: Context) {
        show(context, "Record Added Successfully", "Your health record has been saved.")
    }

    fun recordUpdated(context: Context) {
        show(context, "Record Updated Successfully", "Your health record has been updated.")
    }

    fun recordDeleted(context: Context) {
        show(context, "Record Deleted Successfully", "Your health record has been deleted.")
    }
}
