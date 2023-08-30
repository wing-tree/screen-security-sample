package wing.tree.screen.security.sample.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import wing.tree.screen.security.sample.R
import wing.tree.screen.security.sample.model.CharacterProfile
import wing.tree.screen.security.sample.model.Feed.Companion.EXTRA_NICKNAME
import wing.tree.screen.security.sample.model.Image
import wing.tree.screen.security.sample.ui.theme.ScreenSecuritySampleTheme

class CharacterProfileActivity : ComponentActivity() {
    private val items = listOf(
        CharacterProfile(
            nickname = "Nakiri Ayame",
            image = Image(id = R.drawable.character_profile_nakiri_ayame)
        )
    )

    private fun simulateMessageReceived() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT)

        notificationManager.createNotificationChannel(channel)

        val pendingIntent = Intent(this, ChatRoomActivity::class.java).let {
            PendingIntent.getActivity(
                this,
                100,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = NotificationCompat.Builder(this, "id")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Noah Chat Message")
            .setContentText("HEY~")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nickname = intent.getStringExtra(EXTRA_NICKNAME)
        val item = items.find {
            it.nickname == nickname
        } ?: run {
            finish()

            return
        }

        simulateMessageReceived()

        setContent {
            ScreenSecuritySampleTheme {
                CharacterProfile(characterProfile = item)
            }
        }
    }
}

@Composable
private fun CharacterProfile(
    characterProfile: CharacterProfile,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        characterProfile.image?.let {
            Image(
                painter = painterResource(id = it.id),
                contentDescription = "CharacterProfile"
            )
        }
    }
}
