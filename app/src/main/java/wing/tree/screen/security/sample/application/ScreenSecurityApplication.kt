package wing.tree.screen.security.sample.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.graphics.PixelFormat
import android.view.WindowManager
import android.widget.FrameLayout
import wing.tree.screen.security.sample.annotation.ScreenSecurity
import wing.tree.screen.security.sample.annotation.isNotAnnotationPresent
import wing.tree.screen.security.sample.manager.ScreenSecurityManager

class ScreenSecurityApplication : Application(),
    ActivityLifecycleCallbacks by DefaultActivityLifecycleCallbacks()
{
    private val screenSecurityManager = ScreenSecurityManager.getInstance()

    private var screenSecurityFrameLayout: FrameLayout? = null

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityResumed(activity: Activity) {
        if (activity::class.isNotAnnotationPresent(ScreenSecurity::class)) {
            screenSecurityFrameLayout = FrameLayout(activity).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )

                setOnTouchListener { _, motionEvent ->
                    activity.dispatchTouchEvent(motionEvent)
                    screenSecurityManager.await(activity)

                    false
                }
            }

            val layoutParams = WindowManager.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT
            )

            val windowManager = activity.getSystemService(WindowManager::class.java)

            activity.window?.decorView?.post {
                windowManager.addView(screenSecurityFrameLayout, layoutParams)
            }

            screenSecurityManager.await(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity::class.isNotAnnotationPresent(ScreenSecurity::class)) {
            activity.window?.decorView?.post {
                screenSecurityFrameLayout?.let {
                    val windowManager = activity.getSystemService(WindowManager::class.java)

                    try {
                        windowManager.removeViewImmediate(it)
                    } catch (illegalArgumentException: IllegalArgumentException) {

                    }
                }
            }
        }
    }
}
