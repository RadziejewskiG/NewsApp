package com.radziejewskig.data.base

import com.radziejewskig.data.model.api.ApiModel
import com.radziejewskig.data.model.database.DataModel
import com.radziejewskig.domain.exception.ApiMapperException
import com.radziejewskig.domain.exception.DatabaseMapperException
import com.radziejewskig.domain.exception.DomainMapperException
import com.radziejewskig.domain.model.DomainModel

abstract class DomainToApiMapper<T : DomainModel, V : ApiModel> {

    fun toApi(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw ApiMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}

abstract class ApiToDomainMapper<T : Any, V : DomainModel> {

    fun toDomain(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw DomainMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}

abstract class DomainToDataMapper<T : DomainModel, V : DataModel> {

    fun toData(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw DatabaseMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}

abstract class DataToDomainMapper<T : Any, V : DomainModel> {

    fun toDomain(input: T): V = try {
        map(input)
    } catch (throwable: Throwable) {
        throw DomainMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: T): V
}
