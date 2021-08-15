package com.radziejewskig.presentation.model

data class MatchSchemaPresentationModel (
    val id: Int,
    val teamAName: String,
    val scoreA: Int,
    val teamBName: String,
    val scoreB: Int,
) : PresentationModel
