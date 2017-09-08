package com.vctapps.bustracker.data.notification

import android.util.Log
import com.google.firebase.database.*
import com.vctapps.bustracker.core.BoardDefaults
import com.vctapps.bustracker.core.InvalidData
import io.reactivex.Observable

class NeedsStopRepositoryImpl: NeedsStopRepository {

    private val NEEDS_TO_STOP = "needs_to_stop"

    override fun register(): Observable<String> {
        return Observable.create { emmiter ->
            var nodeNeedsToStop = getNodeNeedsToStop()

            nodeNeedsToStop?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.getValue(String::class.java)

                    if(value != InvalidData.STRING && value != null){
                        Log.d("Firebase", "Needs to stop: " + value)

                        emmiter.onNext(value)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Firebase", "Failed to read value.", error.toException())
                }

            })
        }
    }

    private fun getNodeNeedsToStop(): DatabaseReference? {
        var database = FirebaseDatabase.getInstance()
        var nodeBase = database.getReference(BoardDefaults.ID_MODULE_VALUE.toString())

        nodeBase.setValue(InvalidData.STRING)

        var nodeNeedsToStop = nodeBase.child(NEEDS_TO_STOP)

        nodeNeedsToStop.setValue(InvalidData.STRING)

        return nodeNeedsToStop
    }
}