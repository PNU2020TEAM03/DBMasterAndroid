package com.example.dbmasterandroid.data.dto

data class TableSelectAllDTO(
        val result: String,
        val message: String,
        val value: List<HashMap<String, String>>
)