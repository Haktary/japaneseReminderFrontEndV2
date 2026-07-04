package com.myapp.domain.repository

import com.myapp.core.Result
import com.myapp.domain.model.Vocabulary

interface VocabularyRepository {
    suspend fun getVocabulary(page: Int = 1, pageSize: Int = 20, query: String? = null, jlptLevel: String? = null): Result<List<Vocabulary>>
    suspend fun getById(id: String): Result<Vocabulary>
    suspend fun getDueForReview(limit: Int = 20): Result<List<Vocabulary>>
    suspend fun searchLocal(query: String): List<Vocabulary>
    suspend fun getLocalVocabularyCount(): Int
    suspend fun cacheVocabulary(items: List<Vocabulary>)
}
