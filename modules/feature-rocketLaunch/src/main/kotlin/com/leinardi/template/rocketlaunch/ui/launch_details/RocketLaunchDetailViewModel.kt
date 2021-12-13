/*
 * Copyright 2021 Neha Hasija.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leinardi.template.rocketlaunch.ui.launch_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.leinardi.template.rocketlaunch.ui.launch_details.RocketLaunchDetailsContract.Effect
import com.leinardi.template.rocketlaunch.ui.launch_details.RocketLaunchDetailsContract.Event
import com.leinardi.template.navigation.TemplateNavigator
import com.leinardi.template.navigation.destination.rocketLaunch.RocketLaunchDetailDestination
import com.leinardi.template.rocketserver.repository.LaunchRepository
import com.leinardi.template.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.apollographql.apollo.exception.ApolloException
import com.leinardi.template.rocketlaunch.R
import com.leinardi.template.rocketlaunch.ui.launch_details.RocketLaunchDetailsContract.State
import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchContract
import com.leinardi.template.rocketserver.LaunchDetailsQuery
import com.leinardi.template.rocketserver.LaunchListQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.concurrent.TimeUnit


@HiltViewModel
class RocketLaunchDetailViewModel @Inject constructor(
    private val templateNavigator: TemplateNavigator,
    private val repository: LaunchRepository,
    private val app: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<Event, State, Effect>() {
    private lateinit var clickedLaunchItem: LaunchDetailsQuery.Launch


    override fun provideInitialState() = State(null, true)

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> sendId()
            is Event.OnUpButtonClicked -> templateNavigator.navigateUp()

        }
    }

    init {
        queryLaunchesList(savedStateHandle[RocketLaunchDetailDestination.STRING_PARAM] ?: "")
    }

    private fun sendId() {
        viewModelScope.launch {

            sendEffect { Effect.sendData(clickedLaunchItem) }
        }
    }

    fun queryLaunchesList(launchId: String) = viewModelScope.launch {

        updateState { viewState.value.copy(isLoading = true) }
        try {
            val response = repository.queryLaunchDetails(launchId = launchId)
            clickedLaunchItem = response.data?.launch!!
            updateState {
                viewState.value.copy(clickedLaunchItem, isLoading = false)
            }


        } catch (e: ApolloException) {
            Timber.d("ApolloException", e.printStackTrace().toString())
            updateState { viewState.value.copy(isLoading = false) }
            sendEffect { Effect.ShowSnackbar(app.getString(R.string.i18n_network_issue)) }


        }
    }

}
