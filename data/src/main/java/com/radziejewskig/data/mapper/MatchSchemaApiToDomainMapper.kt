package com.radziejewskig.data.mapper

import com.footballco.recruitment.MatchSchema
import com.radziejewskig.data.base.ApiToDomainMapper
import com.radziejewskig.domain.model.MatchSchemaDomainModel

class MatchSchemaApiToDomainMapper:
    ApiToDomainMapper<MatchSchema, MatchSchemaDomainModel>() {
    override fun map(input: MatchSchema) = MatchSchemaDomainModel (
        id = input.id,
        teamAName = input.teamAName,
        scoreA = input.scoreA,
        teamBName = input.teamBName,
        scoreB = input.scoreB
    )
}