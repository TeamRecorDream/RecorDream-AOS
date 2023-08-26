package com.team.recordream.presentation.record.model

data class EmotionState(
    val selectedEmotion: Int,
    val selected: Boolean,
) {
    companion object {
        private val emotionContainer = mutableListOf<EmotionState>(
            EmotionState(1, false),
            EmotionState(2, false),
            EmotionState(3, false),
            EmotionState(4, false),
            EmotionState(5, false),
        )
        private val SELECTED_RANGE = 1..5
        const val SELECTED_ANYTHING = 6

        fun getEmotionContainer(emotion: Int): List<EmotionState> {
            emotionContainer.forEachIndexed { index, emotionState ->
                emotionContainer[index] = when (emotion) {
                    in SELECTED_RANGE -> emotionState.copy(selected = emotionState.selectedEmotion == emotion)
                    SELECTED_ANYTHING -> emotionState.copy(selected = false)
                    else -> throw IllegalArgumentException("[ERROR} INVALID EMOTION ID")
                }
            }

            return emotionContainer.toList()
        }
    }
}
