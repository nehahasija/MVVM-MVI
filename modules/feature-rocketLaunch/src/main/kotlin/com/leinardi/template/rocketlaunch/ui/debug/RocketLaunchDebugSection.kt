package com.leinardi.template.rocketlaunch.ui.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leinardi.template.ui.component.SettingsGroup
import com.leinardi.template.ui.component.SettingsMenuLink

@Composable
fun RocketLaunchDebugSection(
    modifier: Modifier = Modifier,
) {
    SettingsGroup(
        modifier = modifier,
        title = { Text(text = "Rocket Launch settings") }
    ) {
        Column {
            SettingsMenuLink(
                title = { Text(text = "Server-Url") },
            ) {
            }
            serverUrl(modifier = modifier)
        }
    }
}

@Composable
fun serverUrl(modifier: Modifier){
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        val str = "https://apollo-fullstack-tutorial.herokuapp.com/"
        val startIndex = str.indexOf("https://apollo-fullstack-tutorial.herokuapp.com/")
        val endIndex = startIndex + 48
        append(str)
        addStyle(
            style = SpanStyle(
                color= Color.Blue,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = "https://apollo-fullstack-tutorial.herokuapp.com/",
            start = startIndex,
            end = endIndex
        )

    }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}
@Preview
@Composable
fun RocketLaunchDebugPreview() {
    RocketLaunchDebugSection()
}
