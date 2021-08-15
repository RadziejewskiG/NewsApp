package com.radziejewskig.domain.model

data class MatchSchemaDomainModel (
    val id: Int,
    val teamAName: String,
    val scoreA: Int,
    val teamBName: String,
    val scoreB: Int,
): DomainModel
