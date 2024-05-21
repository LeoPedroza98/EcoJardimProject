package com.pedroza.infnet.ecojardimproject.service

data class JsonPatchOperation(
    val op: String,
    val path: String,
    val value: Any
)
