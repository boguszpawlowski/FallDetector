package bogusz.com.service.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object SharedPreferencesModule {

    @AppScope
    @JvmStatic
    @Provides
    @Named("Default")
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}