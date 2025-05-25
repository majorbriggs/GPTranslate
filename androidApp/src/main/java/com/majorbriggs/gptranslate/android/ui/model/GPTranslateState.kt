
package com.majorbriggs.gptranslate.android.ui.model

import com.majorbriggs.gptranslate.shared.entity.Translation

data class GPTranslateState(
    val isLoading: Boolean = false,
    val translations: List<Translation> = emptyList(),
    val error: String? = null,
)
