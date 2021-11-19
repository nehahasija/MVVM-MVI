package com.leinardi.template.rocketserver.repository

import com.apollographql.apollo.api.Response
import com.leinardi.template.rocketserver.LaunchDetailsQuery
import com.leinardi.template.rocketserver.LaunchListQuery


interface LaunchRepository {

    suspend fun queryLaunchesList(): Response<LaunchListQuery.Data>

    suspend fun queryLaunchDetails(launchId:String) : Response<LaunchDetailsQuery.Data>

}
