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

package com.leinardi.template.foo.ui.debug

import com.leinardi.template.foo.ui.debug.FooDebugContract.Effect
import com.leinardi.template.foo.ui.debug.FooDebugContract.Event
import com.leinardi.template.foo.ui.debug.FooDebugContract.State
import com.leinardi.template.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FooDebugViewModel @Inject constructor() : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State

    override fun handleEvent(event: Event) {
        // no-op
    }
}
