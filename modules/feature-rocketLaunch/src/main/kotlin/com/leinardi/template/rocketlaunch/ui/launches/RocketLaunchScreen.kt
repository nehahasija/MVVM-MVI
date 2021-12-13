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

package com.leinardi.template.rocketlaunch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.leinardi.template.rocketlaunch.R
import com.leinardi.template.ui.component.TopAppBar

import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchContract.*
import com.leinardi.template.rocketlaunch.ui.launches.RocketLaunchViewModel
import com.leinardi.template.rocketserver.LaunchListQuery
import com.leinardi.template.ui.component.AutoSizedCircularProgressIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun RocketLaunchScreen(launchViewModel: RocketLaunchViewModel = hiltViewModel()) {
    RocketLaunchScreen(
        state = launchViewModel.viewState.value,
        sendEvent = { launchViewModel.onUiEvent(it) },
        effectFlow = launchViewModel.effect,
    )
}

@Composable
fun RocketLaunchScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
) {
//    val puppies = remember { DataProvider.puppyList }
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    message = effect.message,
                    duration = SnackbarDuration.Short
                )
            }
        }.collect()
    }
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = stringResource(id = R.string.i18n_rocketLaunch_title))
        },
        content = {
            if (state.isLoading) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AutoSizedCircularProgressIndicator(modifier.size(50.dp))
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = state.rocketLaunchItems,
                    itemContent = {
                        RocketLaunchSiteListItem(launch = it, sendEvent)
                    }
                )
            }
        })
    
}
@Composable
fun RocketLaunchSiteListItem(launch: LaunchListQuery.Launch, sendEvent: (event: Event) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable { sendEvent(Event.OnItemClicked(launch.id)) }) {
            RocketLaunchImage(launch)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = launch.site!!, style = MaterialTheme.typography.h6)
                Text(text = launch.mission!!.name!!, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
private fun RocketLaunchImage(launch: LaunchListQuery.Launch) {
    Image(
        painter = rememberImagePainter(
            data = launch.mission!!.missionPatch ?: R.drawable.placeholder_rocket,
            builder = {
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )


}

