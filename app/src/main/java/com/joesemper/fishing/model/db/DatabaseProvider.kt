package com.joesemper.fishing.model.db

import com.joesemper.fishing.model.entity.map.Marker
import com.joesemper.fishing.model.entity.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DatabaseProvider {
    suspend fun addMarker(marker: Marker)
    suspend fun getAllUserMarkers(): StateFlow<List<Marker?>>
}