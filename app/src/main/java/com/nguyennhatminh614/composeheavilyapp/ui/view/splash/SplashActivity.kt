package com.nguyennhatminh614.composeheavilyapp.ui.view.splash

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nguyennhatminh614.composeheavilyapp.ui.base.BaseComponentActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseComponentActivity<SplashViewModel>() {
    private lateinit var myContext: Context
    override val viewModel: SplashViewModel
        get() = SplashViewModel()


    @Composable
    override fun ProvideCompose() {
        myContext = LocalContext.current
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Splash Screen")
        }
    }

    @Composable
    override fun ProvideComposeLightPreview() {
    }
}