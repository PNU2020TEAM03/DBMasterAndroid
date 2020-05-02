package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import io.reactivex.Single

interface SignUpRepository {

    fun checkNameDuplication(name: HashMap<String, String>): Single<ResponseDTO>

    fun signUp(user: HashMap<String, String>): Single<ResponseDTO>
}