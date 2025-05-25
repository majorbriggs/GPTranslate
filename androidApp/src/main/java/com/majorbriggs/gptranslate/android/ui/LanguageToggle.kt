package com.majorbriggs.gptranslate.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LanguageToggle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "English",
            style = MaterialTheme.typography.bodyMedium
        )
        Switch(checked = true, onCheckedChange = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageChip(modifier: Modifier = Modifier, text: String) {
    var selected by remember { mutableStateOf(true) }
    FilterChip(
        modifier = modifier.padding(horizontal = 4.dp),
        onClick = { selected = !selected },
        label = {
            Text(text)
        },
        selected = selected,
    )
}

@Composable
@Preview
fun LanguageTogglePreview() {
    LanguageToggle()
}

@Composable
@Preview
fun FilterChipPreview() {
    LanguageChip(text = "test")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun LanguageRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        LanguageChip(text = "English")
        LanguageChip(text = "Spanish")
        LanguageChip(text = "French")
        LanguageChip(text = "German")
        LanguageChip(text = "Polish")
    }
}
