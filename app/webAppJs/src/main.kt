package com.example.template

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.template.resources.Res
import com.example.template.resources.app_name
import kotlinx.browser.document
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	ComposeViewport {
		document.title = stringResource(Res.string.app_name)
		WithFontResourcesLoaded {
			App()
		}
	}
}
