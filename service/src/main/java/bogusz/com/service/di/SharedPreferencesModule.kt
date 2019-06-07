package bogusz.com.service.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
object SharedPreferencesModule {

    @AppScope
    @JvmStatic
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, 0)
    }

    private const val PREFERENCES_NAME = "fall_detector_preferences"
}