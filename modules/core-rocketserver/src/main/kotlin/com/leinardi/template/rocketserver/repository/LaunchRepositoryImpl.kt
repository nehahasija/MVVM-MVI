package com.leinardi.template.rocketserver.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.leinardi.template.rocketserver.LaunchDetailsQuery
import com.leinardi.template.rocketserver.LaunchListQuery
import com.leinardi.template.rocketserver.networking.RocketServerApi
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(private val webService : RocketServerApi) : LaunchRepository {
    override suspend fun queryLaunchesList(): Response<LaunchListQuery.Data> {
        return webService.getApolloClient().query(LaunchListQuery()).await()
    }

    override suspend fun queryLaunchDetails(launchId: String): Response<LaunchDetailsQuery.Data> {
        return webService.getApolloClient().query(LaunchDetailsQuery(launchId)).await()
    }

}