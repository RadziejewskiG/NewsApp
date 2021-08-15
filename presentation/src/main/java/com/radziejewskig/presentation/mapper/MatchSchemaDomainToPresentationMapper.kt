package com.radziejewskig.presentation.mapper

import com.radziejewskig.domain.model.MatchSchemaDomainModel
import com.radziejewskig.presentation.base.DomainToPresentationMapper
import com.radziejewskig.presentation.model.MatchSchemaPresentationModel

class MatchSchemaDomainToPresentationMapper:
    DomainToPresentationMapper<MatchSchemaDomainModel, MatchSchemaPresentationModel>() {
    override fun map(input: MatchSchemaDomainModel) = MatchSchemaPresentationModel (
        id = input.id,
        teamAName = input.teamAName,
        scoreA = input.scoreA,
        teamBName = input.teamBName,
        scoreB = input.scoreB
    )
}
