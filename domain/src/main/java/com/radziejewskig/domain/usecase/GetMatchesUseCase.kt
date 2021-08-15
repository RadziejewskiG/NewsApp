package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.model.MatchSchemaDomainModel
import com.radziejewskig.domain.repository.MatchesRepository
import javax.inject.Inject

class GetMatchesUseCase
@Inject constructor(
    private val matchesRepository: MatchesRepository
) {
    suspend operator fun invoke(): List<MatchSchemaDomainModel> = matchesRepository.getMatches()
}
