package com.majorbriggs.gptranslate.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.majorbriggs.gptranslate.android.GPTranslateViewModel
import com.majorbriggs.gptranslate.android.ui.model.GPTranslateState
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun QueryScreen(
    modifier: Modifier = Modifier,
    viewModel: GPTranslateViewModel,
    listState: LazyListState,
) {
    var query by remember { mutableStateOf(TextFieldValue()) }
    val state by viewModel.state.collectAsState(initial = GPTranslateState())
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Word") },
                    placeholder = { Text("Enter a word") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() })

                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            keyboardController?.hide()
                            viewModel.fetchTranslation(query.text, true)
                        }
                    },
                    enabled = !state.isLoading && query.text.isNotEmpty()
                ) {
                    Text("Submit")
                }
            }
        }
        if (state.isLoading) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        state.error?.let { item { Text("Error: $it") } }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        state.translations.forEach {
            item {
                TranslationCard(translation = it) {
                    coroutineScope.launch {
                        viewModel.removeTranslation(it)
                    }
                }
            }
        }
    }
}
