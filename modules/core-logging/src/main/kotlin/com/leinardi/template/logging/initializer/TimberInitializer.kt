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

package com.leinardi.template.logging.initializer

import android.content.Context
import androidx.startup.Initializer
import com.leinardi.template.logging.BuildConfig
import com.leinardi.template.logging.CrashlyticsTree
import com.leinardi.template.logging.DebugTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
