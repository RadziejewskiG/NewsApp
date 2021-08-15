package com.radziejewskig.data.datasource.matches

import com.footballco.recruitment.MatchesApiFake
import com.radziejewskig.data.mapper.MatchSchemaApiToDomainMapper
import com.radziejewskig.domain.model.MatchSchemaDomainModel

class MatchesRemoteDataSource (
    private val matchesApiFake: MatchesApiFake,
    private val matchSchemaApiToDomainMapper: MatchSchemaApiToDomainMapper,
) {

    suspend fun getMatches(): List<MatchSchemaDomainModel> {
        val result = matchesApiFake.getMatches()
        return result.map { matchSchemaApiToDomainMapper.toDomain(it)}
    }

}
