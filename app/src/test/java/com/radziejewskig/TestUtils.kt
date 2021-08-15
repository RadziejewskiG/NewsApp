package com.radziejewskig

import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun callPrivate(objectInstance: Any, methodName: String, vararg args: Any?): Any? {
    val privateMethod: KFunction<*>? =
        objectInstance::class.functions.find { t -> return@find t.name == methodName }

    val argList = args.toMutableList()
    (argList as ArrayList).add(0, objectInstance)
    val argArr = argList.toArray()

    privateMethod?.apply {
        isAccessible = true
        return call(*argArr)
    } ?: throw NoSuchMethodException("Method $methodName does not exist in ${objectInstance::class.qualifiedName}")
    return null
}

suspend fun callPrivateSuspend(objectInstance: Any, methodName: String, vararg args: Any?): Any? {
    val privateMethod: KFunction<*>? =
        objectInstance::class.functions.find { t -> return@find t.name == methodName }

    val argList = args.toMutableList()
    (argList as ArrayList).add(0, objectInstance)
    val argArr = argList.toArray()

    privateMethod?.apply {
        isAccessible = true
        return callSuspend(*argArr)
    } ?: throw NoSuchMethodException("Method $methodName does not exist in ${objectInstance::class.qualifiedName}")
    return null
}

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R
