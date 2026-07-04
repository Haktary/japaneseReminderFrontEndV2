package com.myapp.data.mapper

import com.myapp.data.local.entity.VocabularyEntity
import com.myapp.data.remote.dto.VocabularyResponse
import com.myapp.domain.model.Vocabulary

fun VocabularyResponse.toDomain(): Vocabulary = Vocabulary(
    id = id,
    hiragana = hiragana,
    katakana = katakana,
    kanji = kanji,
    romaji = romaji,
    frenchTranslation = frenchTranslation,
    englishTranslation = englishTranslation,
    exampleSentence = exampleSentence,
    exampleTranslation = exampleTranslation,
    jlptLevel = jlptLevel,
    categoryId = categoryId,
    categoryName = categoryName,
    pitchAccent = pitchAccent,
    tags = tags,
    hasAudio = hasAudio,
    audioUrl = audioUrl,
    isActive = isActive,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun VocabularyResponse.toEntity(): VocabularyEntity = VocabularyEntity(
    id = id,
    hiragana = hiragana,
    katakana = katakana,
    kanji = kanji,
    romaji = romaji,
    frenchTranslation = frenchTranslation,
    englishTranslation = englishTranslation,
    exampleSentence = exampleSentence,
    exampleTranslation = exampleTranslation,
    jlptLevel = jlptLevel,
    categoryId = categoryId,
    categoryName = categoryName,
    pitchAccent = pitchAccent,
    hasAudio = hasAudio,
    audioUrl = audioUrl,
    isActive = isActive,
    tags = if (tags.isNotEmpty()) tags.joinToString(",") else null,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
    createdAt = createdAt?.let { parseTimestamp(it) } ?: System.currentTimeMillis(),
    updatedAt = updatedAt?.let { parseTimestamp(it) } ?: System.currentTimeMillis(),
)

fun VocabularyEntity.toDomain(): Vocabulary = Vocabulary(
    id = id,
    hiragana = hiragana,
    katakana = katakana,
    kanji = kanji,
    romaji = romaji,
    frenchTranslation = frenchTranslation,
    englishTranslation = englishTranslation,
    exampleSentence = exampleSentence,
    exampleTranslation = exampleTranslation,
    jlptLevel = jlptLevel,
    categoryId = categoryId,
    categoryName = categoryName,
    pitchAccent = pitchAccent,
    tags = tags?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList(),
    hasAudio = hasAudio,
    audioUrl = audioUrl,
    isActive = isActive,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
)

fun Vocabulary.toEntity(): VocabularyEntity = VocabularyEntity(
    id = id,
    hiragana = hiragana,
    katakana = katakana,
    kanji = kanji,
    romaji = romaji,
    frenchTranslation = frenchTranslation,
    englishTranslation = englishTranslation,
    exampleSentence = exampleSentence,
    exampleTranslation = exampleTranslation,
    jlptLevel = jlptLevel,
    categoryId = categoryId,
    categoryName = categoryName,
    pitchAccent = pitchAccent,
    hasAudio = hasAudio,
    audioUrl = audioUrl,
    isActive = isActive,
    tags = if (tags.isNotEmpty()) tags.joinToString(",") else null,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
)

private fun parseTimestamp(timestamp: String): Long {
    return try {
        java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }.parse(timestamp)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}
