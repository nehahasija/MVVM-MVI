package com.leinardi.template.rocketlaunch.ui.debug

import com.leinardi.template.ui.base.ViewEffect
import com.leinardi.template.ui.base.ViewEvent
import com.leinardi.template.ui.base.ViewState

class RocketLaunchDebugContract {
    object State : ViewState

    sealed class Event : ViewEvent

    sealed class Effect : ViewEffect
}