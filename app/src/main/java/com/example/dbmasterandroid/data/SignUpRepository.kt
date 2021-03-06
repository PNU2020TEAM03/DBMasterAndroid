package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import io.reactivex.Single

interface SignUpRepository {

    fun checkNameDuplication(name: HashMap<String, String>): Single<ResponseDTO>

    fun signUp(user: HashMap<String, String>): Single<ResponseDTO>

    fun authRequest(email: HashMap<String, String>): Single<ResponseDTO>

    fun authCheck(authInfo: HashMap<String, String>): Single<ResponseDTO>

    fun changePassword(passwordInfo: HashMap<String, String>): Single<ResponseDTO>
}