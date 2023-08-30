package wing.tree.screen.security.sample.manager

import android.app.Activity
import android.content.Intent
import wing.tree.screen.security.sample.activity.ScreenSecurityActivity
import java.util.Timer
import kotlin.concurrent.timerTask

class ScreenSecurityManager private constructor() {
    private val screenSecurityTimeoutInMilliseconds = 5_000L

    private var screenSecured = false
    private var timer: Timer? = null

    fun await(activity: Activity) {
        if (screenSecured) {
            activity.launchScreenSecurityActivity(); return
        }

        timer?.clear()

        timer = Timer().apply {
            schedule(
                timerTask {
                    screenSecured = true

                    activity.launchScreenSecurityActivity()
                },
                screenSecurityTimeoutInMilliseconds
            )
        }
    }

    fun reset() {
        timer?.clear()

        screenSecured = false
        timer = null
    }

    private fun Activity.launchScreenSecurityActivity() {
        val intent = Intent(this, ScreenSecurityActivity::class.java).apply {
            flags = //Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        }

        startActivity(intent)
    }

    private fun Timer.clear() {
        cancel()
        purge()
    }

    companion object {
        private var instance: ScreenSecurityManager? = null

        fun getInstance() = instance ?: ScreenSecurityManager().also {
            instance = it
        }
    }
}
