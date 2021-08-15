package com.radziejewskig.domain.repository

import com.radziejewskig.domain.model.MatchSchemaDomainModel

interface MatchesRepository {

    suspend fun getMatches(): List<MatchSchemaDomainModel>

}
