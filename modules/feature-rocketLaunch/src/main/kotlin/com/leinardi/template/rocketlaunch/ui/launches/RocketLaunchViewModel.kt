/*
 * Copyright 2021 Roberto Leinardi.
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

package com.leinardi.template.rocketlaunch.ui.launches

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchContract.Effect
import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchContract.Event
import com.leinardi.template.navigation.TemplateNavigator
import com.leinardi.template.navigation.destination.rocketLaunch.RocketLaunchDetailDestination
import com.leinardi.template.rocketserver.repository.LaunchRepository
import com.leinardi.template.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.apollographql.apollo.exception.ApolloException
import com.leinardi.template.rocketlaunch.R
import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchContract.State
import com.leinardi.template.rocketserver.LaunchListQuery
import com.leinardi.template.ui.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import timber.log.Timber


@HiltViewModel
class RocketLaunchViewModel @Inject constructor(
    private val templateNavigator: TemplateNavigator,
    private val repository: LaunchRepository,
    private val app: Application
) : BaseViewModel<Event, State, Effect>() {


    override fun provideInitialState() = State(listOf(), true)

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> sendId(event.id)
            is Event.OnUpButtonClicked -> templateNavigator.navigateUp()
        }
    }

    init {
        queryLaunchesList()
    }

    private fun sendId(id: String) {
        viewModelScope.launch {
            templateNavigator.navigate(RocketLaunchDetailDestination.createRoute(id))
        }
    }

    fun queryLaunchesList() = viewModelScope.launch {
        updateState { viewState.value.copy(isLoading = true) }
        try {
            val response = repository.queryLaunchesList()
            val newLaunches = response.data?.launches?.launches?.filterNotNull()
            updateState {
                copy(newLaunches!!, false)
            }

        } catch (e: ApolloException) {
            Timber.d("ApolloException", e.printStackTrace().toString())
            updateState { viewState.value.copy(isLoading = false) }
            sendEffect { Effect.ShowSnackbar(app.getString(R.string.i18n_network_issue)) }


        }
    }


}
