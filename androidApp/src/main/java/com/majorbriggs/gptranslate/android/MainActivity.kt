package com.majorbriggs.gptranslate.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.majorbriggs.gptranslate.android.ui.QueryScreen
import com.majorbriggs.gptranslate.shared.GPTranslateSDK
import com.majorbriggs.gptranslate.shared.cache.DatabaseDriverFactory

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GPTranslateViewModel>(
        factoryProducer = {
            GPTranslateViewModelFactory(GPTranslateSDK(DatabaseDriverFactory(this)))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GPTranslateApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GPTranslateApp(viewModel: GPTranslateViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("GPTranslate")
                },
                scrollBehavior = scrollBehavior,
            )
        },

        ) { contentPadding ->
        val listState = rememberLazyListState()
        QueryScreen(modifier = Modifier.padding(contentPadding), viewModel, listState)
    }
}
