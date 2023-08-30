package wing.tree.screen.security.sample.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import wing.tree.screen.security.sample.ui.theme.ScreenSecuritySampleTheme

class ChatRoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenSecuritySampleTheme {
                Text(text = "ChatRoom")
            }
        }
    }
}
