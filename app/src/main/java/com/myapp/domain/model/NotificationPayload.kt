package com.myapp.domain.model

data class NotificationPayload(
    val vocabularyId: String,
    val hiragana: String,
    val katakana: String? = null,
    val kanji: String? = null,
    val romaji: String? = null,
    val frenchTranslation: String,
    val englishTranslation: String? = null,
    val audioUrl: String? = null,
)
