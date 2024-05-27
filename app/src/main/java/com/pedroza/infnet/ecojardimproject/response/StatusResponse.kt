package com.pedroza.infnet.ecojardimproject.response

import com.pedroza.infnet.ecojardimproject.models.Status

data class StatusResponse(
    val items: List<Status>,
    val count: Int
)
