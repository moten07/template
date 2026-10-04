package com.example.template

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.template.ui.theme.Pink40
import com.example.template.ui.theme.Pink80
import com.example.template.ui.theme.Purple40
import com.example.template.ui.theme.Purple80
import com.example.template.ui.theme.PurpleGrey40
import com.example.template.ui.theme.PurpleGrey80

interface Platform {
	val name: String

	@Composable
	fun dynamicColorScheme(
		darkTheme: Boolean,
		dynamicColor: Boolean
	): ColorScheme {
		return if (darkTheme) DarkColorScheme else LightColorScheme
	}
}

expect fun getPlatform(): Platform


private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40

	/* Other default colors to override
	background = Color(0xFFFFFBFE),
	surface = Color(0xFFFFFBFE),
	onPrimary = Color.White,
	onSecondary = Color.White,
	onTertiary = Color.White,
	onBackground = Color(0xFF1C1B1F),
	onSurface = Color(0xFF1C1B1F),
	*/
)
