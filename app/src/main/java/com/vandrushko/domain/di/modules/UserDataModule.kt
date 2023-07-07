package com.vandrushko.domain.di.modules

import com.vandrushko.data.UserDataHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserDataModule {

    @Provides
    @Singleton
    fun provideUserDataHolder(): UserDataHolder {
        return UserDataHolder()
    }
}