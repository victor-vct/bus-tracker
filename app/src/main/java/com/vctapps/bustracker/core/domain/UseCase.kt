package com.vctapps.bustracker.core.domain

interface UseCase<T> {

    fun run(): T
}