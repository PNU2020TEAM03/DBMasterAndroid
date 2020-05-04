package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.ConnectionRepository
import com.example.dbmasterandroid.data.api.ConnectionService
import com.example.dbmasterandroid.data.dto.ConnectionDTO
import io.reactivex.Single

class ConnectionRepositoryImpl(
        private val connectionService: ConnectionService
): ConnectionRepository {

    override fun connectionCheck(user: HashMap<String, String>): Single<ConnectionDTO> {
        return connectionService.connectionCheck(user)
    }

}