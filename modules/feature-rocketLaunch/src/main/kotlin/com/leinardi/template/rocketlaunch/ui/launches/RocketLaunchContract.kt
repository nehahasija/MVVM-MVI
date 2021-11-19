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

import com.leinardi.template.rocketserver.LaunchListQuery
import com.leinardi.template.ui.base.ViewEffect
import com.leinardi.template.ui.base.ViewEvent
import com.leinardi.template.ui.base.ViewState

class RocketLaunchContract {

    data class State(
        val rocketLaunchItems: List<LaunchListQuery.Launch> = listOf(),
        val isLoading: Boolean = false
    ) : ViewState


    sealed class Event : ViewEvent {
        data class OnItemClicked(val id: String) : Event()
        object OnUpButtonClicked : Event()

    }

    sealed class Effect : ViewEffect{
        data class ShowSnackbar(val message: String) : Effect()

    }

}
