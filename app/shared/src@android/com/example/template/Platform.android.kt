package com.example.template

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class AndroidPlatform : Platform {
	override val name: String = "Android ${Build.VERSION.SDK_INT}"

	@Composable
	override fun dynamicColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme {
		val context = LocalContext.current
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
			&& dynamicColor
		) {
			return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
		}
		return super.dynamicColorScheme(darkTheme, dynamicColor)
	}

}

actual fun getPlatform(): Platform = AndroidPlatform()
