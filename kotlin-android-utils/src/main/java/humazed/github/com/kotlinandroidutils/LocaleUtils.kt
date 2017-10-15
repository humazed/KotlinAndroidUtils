package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.support.annotation.StyleRes
import android.view.ContextThemeWrapper
import humazed.github.com.kotlinandroidutils.Language.ARABIC
import java.util.*

val KEY_LANGUAGE = "key_language"

/**
 * @param  base the context for activity
 * @param themeResId activity theme ex: R.style.AppTheme
 */
class ContextLocale(base: Context, @StyleRes themeResId: Int) : ContextThemeWrapper(base, themeResId) {
    companion object {
        @Suppress("DEPRECATION")
        fun wrap(context: Context, @StyleRes themeResId: Int): ContextThemeWrapper {
            var context = context
            val language = context.getLanguage()

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
            return ContextLocale(context, themeResId)
        }

        @Suppress("DEPRECATION")
        private fun setSystemLocaleCompat(config: Configuration, locale: Locale) =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    config.setLocale(locale)
                } else config.locale = locale

    }
}

@Suppress("DEPRECATION")
fun Context.getSystemLocaleCompat(): Locale {
    val config = resources.configuration
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) config.locales.get(0)
    else config.locale
}

fun Context.saveLanguage(language: Language) {
    getDefaultSharedPreferences(this)
            .edit()
            .putString(KEY_LANGUAGE, language.value)
            .apply()
}


fun Activity.saveLanguageAndRestart(language: Language) {
    saveLanguage(language)
    restart()
}

fun Context.getLanguage(): Language =
        Language::class.decode(getDefaultSharedPreferences(this)
                .getString(KEY_LANGUAGE, ARABIC.value))


enum class Language(val value: String) : Codified<String> {
    ENGLISH("en"), ARABIC("ar");

    override val code = value
}