package com.majorbriggs.gptranslate.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorbriggs.gptranslate.android.ui.model.GPTranslateState
import com.majorbriggs.gptranslate.shared.GPTranslateSDK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GPTranslateViewModel(private val sdk: GPTranslateSDK) : ViewModel() {

    private val _state = MutableStateFlow(GPTranslateState())
    val state: Flow<GPTranslateState> = _state

    init {
        viewModelScope.launch {
            fetchTranslation("", false)
        }
    }

    suspend fun fetchTranslation(query: String, needsReload: Boolean) {
        _state.update { it.copy(isLoading = true) }
        runCatching {
            sdk.getTranslations(query, needsReload)
        }.onSuccess { translations ->
            _state.update { it.copy(translations = translations, isLoading = false, error = null) }
        }.onFailure { error ->
            _state.update { it.copy(error = error.localizedMessage ?: "Unknown error", isLoading = false) }
        }
    }

    suspend fun removeTranslation(id: String){
        sdk.deleteTranslation(id)
        val translations = sdk.getTranslations("", false)
        _state.update { it.copy(translations = translations, isLoading = false, error = null) }
    }
}
