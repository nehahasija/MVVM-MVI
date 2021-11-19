package com.leinardi.template.rocketlaunch.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.leinardi.template.rocketlaunch.R
import com.leinardi.template.rocketlaunch.ui.launch_details.RocketLaunchDetailViewModel
import com.leinardi.template.rocketlaunch.ui.launch_details.RocketLaunchDetailsContract.*
import com.leinardi.template.rocketlaunch.utils.baselineHeight
import com.leinardi.template.rocketserver.LaunchDetailsQuery
import com.leinardi.template.ui.component.*
import com.leinardi.template.ui.theme.TemplateDarkColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun RocketLaunchDetailScreen(launchDetailViewModel: RocketLaunchDetailViewModel = hiltViewModel()) {

    RocketLaunchDetailScreen(
        state = launchDetailViewModel.viewState.value,
        sendEvent = { launchDetailViewModel.onUiEvent(it) },
        effectFlow = launchDetailViewModel.effect,
        isLoading = launchDetailViewModel.viewState.value.isLoading
    )

}

@Composable
fun RocketLaunchDetailScreen(
    state: State,
    sendEvent: (event: Event) -> Unit,
    effectFlow: Flow<Effect>,
    isLoading: Boolean,
    modifier: Modifier = Modifier

) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    message = effect.message,
                    duration = SnackbarDuration.Short
                )

                else -> scaffoldState.snackbarHostState.showSnackbar(
                    message = "No data",
                    duration = SnackbarDuration.Short
                )
            }
        }.collect()
    }
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = stringResource(id = R.string.i18n_rocketLaunchDetail_title),
                navigateUp = { sendEvent(Event.OnUpButtonClicked) })
        },
        content = {
            state.launch?.let { loadUI(param = state.launch) }

            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AutoSizedCircularProgressIndicator(modifier.size(50.dp))
                }
            }
        })

}


@Composable
private fun loadUI(param: LaunchDetailsQuery.Launch) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {

                    Header(
                        scrollState,
                        param,
                        this@BoxWithConstraints.maxHeight
                    )
                    Content(param, this@BoxWithConstraints.maxHeight)

                    Column( ) {
                        BookNowFab(
                            extended = scrollState.value == 0,param.isBooked
                        )
                    }

                }
            }

        }
    }
}

@Composable
private fun Header(
    scrollState: ScrollState,
    launch: LaunchDetailsQuery.Launch,
    containerHeight: Dp
) {


    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    MediaQuery(comparator = Dimensions.Width lessThan 400.dp) {
        Spacer(modifier = Modifier.height(16.dp))

        val padding = 16.dp
        Image(
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .padding(padding),
            painter = rememberImagePainter(
                data = launch.mission!!.missionPatch ?: R.drawable.placeholder_rocket
            ),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
    }
    MediaQuery(comparator = Dimensions.Width greaterThan 400.dp) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .mediaQuery(
                    comparator = Dimensions.Width greaterThan 400.dp,
                    modifier = Modifier.padding(offsetDp)
                ),
            painter = rememberImagePainter(
                data = launch.mission!!.missionPatch ?: R.drawable.placeholder_rocket
            ),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
    }
}

@Composable

private fun Content(launch: LaunchDetailsQuery.Launch, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Name(launch)
        Property(
            stringResource(R.string.i18n_rocketLaunch_site),
            launch.site ?: ""
        )
        Property(stringResource(R.string.i18n_rocketLaunch_rocket), launch.rocket?.name ?: "")

        Property(
            stringResource(R.string.i18n_rocketLaunch_rocket_type),
            launch.rocket?.type ?: ""
        )

    }
}

@Composable
private fun Name(
    launch: LaunchDetailsQuery.Launch
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Name(
            launch = launch,
            modifier = Modifier.baselineHeight(32.dp)
        )
    }
}

@Composable
private fun Name(launch: LaunchDetailsQuery.Launch, modifier: Modifier = Modifier) {
    Text(
        text = launch.mission!!.name!!,
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Property(label: String, value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = label,
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.caption,
            )
        }
        val style = if (isLink) {
            MaterialTheme.typography.body1.copy(color = TemplateDarkColors.primary)
        } else {
            MaterialTheme.typography.body1
        }
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = style
        )
    }

}

@Composable
fun BookNowFab(extended: Boolean, isBooked:Boolean,modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { /* TODO */ },
        modifier = modifier
            .padding(20.dp)
            .padding()
            .height(48.dp)
            .widthIn(min = 100.dp),
        backgroundColor = TemplateDarkColors.primary,
        contentColor = Color.White
    ) {
        if (isBooked)
        Text(text = stringResource(R.string.i18n_rocketLaunch_booked),
        textAlign = TextAlign.Center)
        else
            Text(text = stringResource(R.string.i18n_rocketLaunch_book_now))

    }
}

@Preview
@Composable
fun RocketLaunchDetailsScreenPreview() {
    RocketLaunchDetailScreen()
}

