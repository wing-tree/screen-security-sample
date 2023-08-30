package wing.tree.screen.security.sample.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import wing.tree.screen.security.sample.annotation.ScreenSecurity
import wing.tree.screen.security.sample.manager.ScreenSecurityManager
import wing.tree.screen.security.sample.ui.theme.ScreenSecuritySampleTheme

@ScreenSecurity
class ScreenSecurityActivity : ComponentActivity() {
    private val screenSecurityManager = ScreenSecurityManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenSecuritySampleTheme {
                BackHandler {
                    /* no-op */
                }

                Button(
                    onClick = {
                        screenSecurityManager.reset()
                        finish()
                    }
                ) {
                    Text(text = "CORRECT PIN")
                }
            }
        }
    }
}
