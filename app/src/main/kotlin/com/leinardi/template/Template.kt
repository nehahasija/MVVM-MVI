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

package com.leinardi.template

import android.app.Application
import android.os.SystemClock
import com.leinardi.template.bar.feature.BarFeature
import com.leinardi.template.debug.feature.DebugFeature
import com.leinardi.template.feature.FeatureManager
import com.leinardi.template.foo.feature.FooFeature
import com.leinardi.template.rocketlaunch.feature.RocketDetailFeature
import com.leinardi.template.rocketlaunch.feature.RocketFeature

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Template : Application() {
    @Inject lateinit var featureManager: FeatureManager

    override fun onCreate() {
        super.onCreate()
        registerFeatures()
        simulateHeavyLoad()
    }

    @Suppress("MagicNumber")
    private fun simulateHeavyLoad() {
        SystemClock.sleep(1500)
    }

    private fun registerFeatures() {
        featureManager.register(
            listOf(
                FooFeature(),
                BarFeature(),
                DebugFeature(),
                RocketFeature(),
                RocketDetailFeature(),

            )
        )
    }
}
