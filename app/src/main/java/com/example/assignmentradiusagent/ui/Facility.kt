package com.example.assignmentradiusagent.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
    fun Facility(name: String, modifier: Modifier = Modifier) {
        Text(
            text = name,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }