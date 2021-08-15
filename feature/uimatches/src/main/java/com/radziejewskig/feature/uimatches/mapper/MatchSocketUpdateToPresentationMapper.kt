package com.radziejewskig.feature.uimatches.mapper

import com.footballco.recruitment.MatchSocketUpdate
import com.radziejewskig.presentation.base.AnyToPresentationMapper
import com.radziejewskig.presentation.model.MatchSocketUpdatePresentationModel

class MatchSocketUpdateToPresentationMapper:
    AnyToPresentationMapper<MatchSocketUpdate, MatchSocketUpdatePresentationModel>() {

    override fun map(input: MatchSocketUpdate) = MatchSocketUpdatePresentationModel (
        id = input.id,
        scoreA = input.scoreA,
        scoreB = input.scoreB
    )

}
