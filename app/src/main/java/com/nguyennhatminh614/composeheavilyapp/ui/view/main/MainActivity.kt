package com.nguyennhatminh614.composeheavilyapp.ui.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nguyennhatminh614.composeheavilyapp.ui.base.BaseComponentActivity
import com.nguyennhatminh614.composeheavilyapp.ui.view.main.MainViewModel

class MainActivity : BaseComponentActivity<MainViewModel>() {
    override val viewModel: MainViewModel
        get() = MainViewModel()

    @Composable
    override fun ProvideCompose() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Main Screen")
        }
    }

    @Composable
    override fun ProvideComposeLightPreview() {
    }
}