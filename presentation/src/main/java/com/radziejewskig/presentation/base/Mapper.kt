package com.radziejewskig.presentation.base

import com.radziejewskig.domain.exception.DomainMapperException
import com.radziejewskig.domain.exception.PresentationMapperException
import com.radziejewskig.domain.model.DomainModel
import com.radziejewskig.presentation.model.PresentationModel

abstract class DomainToPresentationMapper<T : DomainModel, V : PresentationModel> {

    fun toPresentation(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw PresentationMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}

abstract class PresentationToDomainMapper<T : PresentationModel, V : DomainModel> {

    fun toDomain(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw DomainMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}

abstract class AnyToPresentationMapper<T : Any, V : PresentationModel> {

    fun toPresentation(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw PresentationMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}
