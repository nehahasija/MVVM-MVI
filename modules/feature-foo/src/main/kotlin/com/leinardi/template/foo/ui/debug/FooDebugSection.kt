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

package com.leinardi.template.foo.ui.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leinardi.template.ui.component.SettingsGroup
import com.leinardi.template.ui.component.SettingsMenuLink

@Composable
fun FooDebugSection(
    modifier: Modifier = Modifier,
) {
    SettingsGroup(
        modifier = modifier,
        title = { Text(text = "Foo settings") }
    ) {
        Column {
            SettingsMenuLink(
                title = { Text(text = "TBD") },
            ) {
            }
        }
    }
}

@Preview
@Composable
fun FooDebugPreview() {
    FooDebugSection()
}
