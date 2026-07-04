package com.myapp.data.repository

import com.myapp.core.Result
import com.myapp.data.local.dao.VocabularyDao
import com.myapp.data.mapper.toDomain
import com.myapp.data.mapper.toEntity
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.domain.repository.VocabularyRepository
import com.myapp.domain.model.Vocabulary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabularyRepositoryImpl @Inject constructor(
    private val api: JapaneseApi,
    private val vocabularyDao: VocabularyDao,
) : VocabularyRepository {

    override suspend fun getVocabulary(page: Int, pageSize: Int, query: String?, jlptLevel: String?): Result<List<Vocabulary>> = Result.wrap {
        val response = api.getVocabulary(page = page, pageSize = pageSize, query = query, jlptLevel = jlptLevel)
        if (response.isSuccessful) {
            val paginated = response.body() ?: throw Exception("Empty response")
            val items = paginated.items.map { it.toDomain() }
            vocabularyDao.insertAll(paginated.items.map { it.toEntity() })
            items
        } else {
            val cached = vocabularyDao.getVocabulary(query = query, jlptLevel = jlptLevel, limit = pageSize, offset = (page - 1) * pageSize)
            if (cached.isNotEmpty()) {
                cached.map { it.toDomain() }
            } else {
                throw Exception("Failed to fetch vocabulary")
            }
        }
    }

    override suspend fun getById(id: String): Result<Vocabulary> = Result.wrap {
        val cached = vocabularyDao.getById(id)
        if (cached != null) {
            cached.toDomain()
        } else {
            val response = api.getVocabularyById(id)
            if (response.isSuccessful) {
                val item = response.body() ?: throw Exception("Empty response")
                vocabularyDao.insert(item.toEntity())
                item.toDomain()
            } else {
                throw Exception("Vocabulary not found")
            }
        }
    }

    override suspend fun getDueForReview(limit: Int): Result<List<Vocabulary>> = Result.wrap {
        val currentTime = System.currentTimeMillis() / 1000.0
        val dueItems = vocabularyDao.getDueForReview(currentTime, limit)
        if (dueItems.isNotEmpty()) {
            dueItems.map { it.toDomain() }
        } else {
            val response = api.getDueVocabulary(limit)
            if (response.isSuccessful) {
                val dueResponse = response.body() ?: throw Exception("Empty response")
                vocabularyDao.insertAll(dueResponse.items.map { it.toEntity() })
                dueResponse.items.map { it.toDomain() }
            } else {
                emptyList()
            }
        }
    }

    override suspend fun searchLocal(query: String): List<Vocabulary> {
        return vocabularyDao.search(query).map { it.toDomain() }
    }

    override suspend fun getLocalVocabularyCount(): Int {
        return vocabularyDao.getCount()
    }

    override suspend fun cacheVocabulary(items: List<Vocabulary>) {
        vocabularyDao.insertAll(items.map { it.toEntity() })
    }
}
