package com.example.dbmasterandroid.data.dto

data class TableRowDataDTO(
        val result: String,
        val message: String,
        val value: List<HashMap<String, String>>
)