package com.leinardi.template.rocketserver.di

import com.leinardi.template.rocketserver.repository.LaunchRepository
import com.leinardi.template.rocketserver.repository.LaunchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repo: LaunchRepositoryImpl): LaunchRepository



}