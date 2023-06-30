package com.example.assignmentradiusagent.model.dataclass

import com.example.assignmentradiusagent.model.database.OptionEntity

data class Facilities(
    val facility_id: String,
    val name: String,
    val options: List<OptionEntity>,
    var selectedOptionId: String = "",
    var disabledOptionId: String = ""
)
