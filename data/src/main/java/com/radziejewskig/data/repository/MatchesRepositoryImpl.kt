package com.radziejewskig.data.repository

import com.radziejewskig.data.datasource.matches.MatchesRemoteDataSource
import com.radziejewskig.domain.model.MatchSchemaDomainModel
import com.radziejewskig.domain.repository.MatchesRepository

class MatchesRepositoryImpl(
    private val matchesRemoteDataSource: MatchesRemoteDataSource
): MatchesRepository {

    override suspend fun getMatches(): List<MatchSchemaDomainModel> {
        return matchesRemoteDataSource.getMatches()
    }

}
