package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ConnectionDTO
import io.reactivex.Single

interface ConnectionRepository {

    fun connectionCheck(user: HashMap<String, String>): Single<ConnectionDTO>
}