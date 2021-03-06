package com.joesemper.fishing.data.datasource

import com.joesemper.fishing.data.entity.raw.RawUserCatch
import com.joesemper.fishing.data.entity.raw.RawMapMarker
import com.joesemper.fishing.data.entity.content.UserMapMarker
import com.joesemper.fishing.data.entity.common.Progress
import com.joesemper.fishing.data.entity.content.MapMarker
import com.joesemper.fishing.data.entity.content.UserCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DatabaseProvider {
    suspend fun addNewCatch(newCatch: RawUserCatch): StateFlow<Progress>
    suspend fun addNewMarker(newMarker: RawMapMarker): StateFlow<Progress>
    suspend fun deleteMarker(userCatch: UserCatch)
    fun getMapMarker(markerId: String): Flow<UserMapMarker?>
    fun getAllMarkers(): Flow<MapMarker>
    fun getAllUserMarkersList(): Flow<List<MapMarker>>
    fun getAllUserCatches(): Flow<List<UserCatch>>
    fun getCatchesByMarkerId(markerId: String): Flow<List<UserCatch>>
}