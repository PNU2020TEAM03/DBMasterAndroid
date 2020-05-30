package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.data.api.AuthService
import com.example.dbmasterandroid.data.api.SignUpService
import com.example.dbmasterandroid.data.dto.ResponseDTO
import io.reactivex.Single

class SignUpRepositoryImpl (
    private val signUpService: SignUpService,
    private val authService: AuthService
): SignUpRepository {

    override fun checkNameDuplication(name: HashMap<String, String>): Single<ResponseDTO> {
        return signUpService.checkNameDuplication(name)
    }

    override fun signUp(user: HashMap<String, String>): Single<ResponseDTO> {
        return signUpService.signUp(user)
    }

    override fun authRequest(email: HashMap<String, String>): Single<ResponseDTO> {
        return authService.authRequest(email)
    }

}