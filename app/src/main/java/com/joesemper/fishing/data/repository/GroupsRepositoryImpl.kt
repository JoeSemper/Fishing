package com.joesemper.fishing.data.repository

import androidx.lifecycle.LiveData
import com.joesemper.fishing.data.datasource.DatabaseProvider
import com.joesemper.fishing.data.entity.common.User

class GroupsRepositoryImpl(private val provider: DatabaseProvider): GroupsRepository {
    override suspend fun getUserData(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun logoutCurrentUser(): LiveData<Boolean> {
        TODO("Not yet implemented")
    }


}