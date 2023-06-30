package com.example.assignmentradiusagent.view

import com.example.assignmentradiusagent.model.dataclass.Facilities
import com.example.assignmentradiusagent.model.database.OptionEntity

interface DataView {
    fun showLoading()
    fun hideLoading()
    fun displayFacilities(
        facilities: List<Facilities>,
        exclusionMap: MutableMap<String, String>
    )
    fun showError(errorMessage: String)
}