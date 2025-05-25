package com.majorbriggs.gptranslate.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.majorbriggs.gptranslate.shared.GPTranslateSDK

class GPTranslateViewModelFactory(private val sdk: GPTranslateSDK) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GPTranslateViewModel::class.java)) {
            return GPTranslateViewModel(sdk) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
