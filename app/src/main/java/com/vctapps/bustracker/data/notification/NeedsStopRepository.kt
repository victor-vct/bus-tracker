package com.vctapps.bustracker.data.notification

import io.reactivex.Observable

interface NeedsStopRepository {

    fun register(): Observable<String>

}