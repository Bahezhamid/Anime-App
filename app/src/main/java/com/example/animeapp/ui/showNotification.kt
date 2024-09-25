import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.animeapp.R

fun showSuccessNotification(
    context: Context,
    isPasswordChange: Boolean,
    notificationId: Int = 1
) {
    val channelId = "success_channel_id"
    val title = "Success"
    val message = if (isPasswordChange) {
        "Password changed successfully"
    } else {
        "Email changed successfully"
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = "Success Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel for success notifications"
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_app_icon) // Your custom icon
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH) // Set priority to high
        .setDefaults(NotificationCompat.DEFAULT_ALL) // Enable vibration, sound, etc.
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notify(notificationId, builder.build())
    }
}
