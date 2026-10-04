package com.example.template.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.template.getPlatform

@Composable
fun MyTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = true,
	content: @Composable () -> Unit
) {
	println("Reloading theme, darkTheme=$darkTheme, dynamicColor=$dynamicColor")
	val platform = getPlatform()
	val colorScheme = platform.dynamicColorScheme(darkTheme, dynamicColor)

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}
