package com.leinardi.template.rocketlaunch.ui.debug

import com.leinardi.template.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.leinardi.template.rocketlaunch.ui.debug.RocketLaunchDebugContract.Event
import com.leinardi.template.rocketlaunch.ui.debug.RocketLaunchDebugContract.State
import com.leinardi.template.rocketlaunch.ui.debug.RocketLaunchDebugContract.Effect



@HiltViewModel
class RocketLaunchDebugViewModel @Inject constructor() : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State

    override fun handleEvent(event: Event) {
        // no-op
    }
}