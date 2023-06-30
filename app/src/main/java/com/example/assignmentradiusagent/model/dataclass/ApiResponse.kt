package com.example.assignmentradiusagent.model.dataclass

data class ApiResponse(
    val facilities: List<Facility>,
    val exclusions: List<List<Exclusion>>
)