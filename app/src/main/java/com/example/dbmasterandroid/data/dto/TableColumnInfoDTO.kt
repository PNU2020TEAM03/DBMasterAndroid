package com.example.dbmasterandroid.data.dto

import com.google.gson.annotations.SerializedName

data class TableColumnInfoDTO(
        val primaryKey: String,
        val columnName: String,
        @SerializedName("datatype")
        val dataType: String,
        @SerializedName("columnsize")
        val columnSize: String,
        @SerializedName("decimaldigits")
        val decimalDigits: String?
)