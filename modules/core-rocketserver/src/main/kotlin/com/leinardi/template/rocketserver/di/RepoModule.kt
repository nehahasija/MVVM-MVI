package com.leinardi.template.rocketserver.di

import android.content.Context
import com.leinardi.template.rocketserver.networking.RocketServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideWebService(@ApplicationContext context:Context) = RocketServerApi(context)

}