package com.vandrushko.domain.di.modules

import android.content.Context
import androidx.room.Room
import com.vandrushko.data.db.UserDao
import com.vandrushko.data.db.UserDataBase
import com.vandrushko.ui.fragments.Configs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(userDatabase: UserDataBase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): UserDataBase {
        return Room.databaseBuilder(
            appContext,
            UserDataBase::class.java,
            Configs.USER_DB_NAME
        ).build()
    }
}