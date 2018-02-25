package humazed.github.com.kotlinandroidutils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.support.annotation.StyleRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.ContextThemeWrapper
import java.util.*

const val KEY_LANGUAGE = "key_language"

/**
 * @param  base the context for activity
 * @param themeResId activity theme ex: R.style.AppTheme
 */
class ContextLocale(base: Context, @StyleRes themeResId: Int) : ContextThemeWrapper(base, themeResId)

/**
 * @param  context the context for activity.
 * @param activityLanguage the language of the activity regardless of selected language.
 *
 * **Note:** the [activityLanguage] doesn't impact the application language only the current activity.
 * when set shouldn't to use [onResumeLocaleDelegate]
 */
@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun wrap(context: Context, activityLanguage: Language? = null): ContextThemeWrapper {
    var context = context
    val language = activityLanguage ?: context.getLanguage()

    val config = context.resources.configuration

    if (language.value.isNotBlank()) {
        val locale = Locale(language.value)
        Locale.setDefault(locale)
        setSystemLocaleCompat(config, locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(locale)
            context = context.createConfigurationContext(config)
        } else context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
    return ContextLocale(context, getThemeId(context))
}

@Suppress("DEPRECATION")
private fun setSystemLocaleCompat(config: Configuration, locale: Locale) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) config.setLocale(locale)
    else config.locale = locale
}

private fun getThemeId(context: Context): Int {
    return try {
        val wrapper = ContextThemeWrapper::class.java
        val method = wrapper.getMethod("getThemeResId")
        method.isAccessible = true
        method.invoke(context) as Int
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

/**
 * Call this method on the Activity onResume. It will recreate the Activity if a Locale change is detected.
 */
fun Activity.onResumeLocaleDelegate() {
    val previousLocale = getCurrentLocaleCompat().language
    val savedLanguage = getDefaultSharedPreferences(this).getString(KEY_LANGUAGE, "")
    val shouldRestartActivity = savedLanguage.isNotBlank() && previousLocale != savedLanguage
    if (shouldRestartActivity) recreate(this)
}


/**
 * helper methods
 */
fun Context.setLanguage(language: Language) {
    getDefaultSharedPreferences(this).edit {
        putString(KEY_LANGUAGE, language.value)
    }
}

fun Activity.setLanguageAndRestart(language: Language) {
    setLanguage(language)
    recreate(this)
}

fun Context.getLanguage(): Language {
    val deviceLanguage = try {
        Language::class.decode(getCurrentLocaleCompat().language)
    } catch (e: Exception) {
        Language::class.decode("en")
    }

    return Language::class.decode(getDefaultSharedPreferences(this)
            .getString(KEY_LANGUAGE, deviceLanguage.value))
}

@Suppress("DEPRECATION")
fun Context.getCurrentLocaleCompat(): Locale {
    val config = resources.configuration
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) config.locales.get(0)
    else config.locale
}

/**
 * Helper method to recreate the Activity. This method should be called after a Locale change.
 *
 * @param activity the Activity that will be recreated
 * @param animate  a flag indicating if the recreation will be animated or not
 */
fun recreate(activity: Activity, animate: Boolean = true) {
    if (animate) {
        val restartIntent = Intent(activity, activity::class.java)

        val extras = activity.intent.extras
        if (extras != null) {
            restartIntent.putExtras(extras)
        }
        ActivityCompat.startActivity(
                activity, restartIntent,
                ActivityOptionsCompat
                        .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                        .toBundle()
        )
        activity.finish()
    } else {
        activity.recreate()
    }
}


enum class Language(val value: String) : Codified<String> {
    ENGLISH("en"), ARABIC("ar");

    override val code = value
}