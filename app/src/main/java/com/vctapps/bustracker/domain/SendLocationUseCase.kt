package com.vctapps.bustracker.domain

import com.vctapps.bustracker.core.domain.UseCase
import io.reactivex.Completable

interface SendLocationUseCase : UseCase<Completable> {

    fun stop(): Completable

}