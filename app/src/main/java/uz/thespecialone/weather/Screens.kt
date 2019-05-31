package uz.thespecialone.weather

import android.content.Context
import android.content.Intent
import ru.terrakok.cicerone.android.support.SupportAppScreen
import uz.thespecialone.weather.ui.info.InfoFragment
import uz.thespecialone.weather.ui.main.MainActivity

object Screens {
    object MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent =
            Intent(context, MainActivity::class.java)
    }

    class InfoScreen(private val id: Long) : SupportAppScreen() {
        override fun getFragment() = InfoFragment.getNewInstance(id)

    }
}