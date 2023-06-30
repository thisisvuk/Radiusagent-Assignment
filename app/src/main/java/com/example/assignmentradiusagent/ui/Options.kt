package com.example.assignmentradiusagent.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.assignmentradiusagent.R

@Composable
    fun Options(
    name: String,
    icon: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
    ) {
        val isSelectedAndNotDisabled = isSelected && !isDisabled
        val containerColor = if (isDisabled) {
            Color.Gray.copy(alpha = 0.2f)
        } else if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.background.copy(alpha = 0.1f)
        }

        val contentColor = if (isSelectedAndNotDisabled) {
            Color.White
        } else {
             Color.Black
        }

        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .wrapContentSize()
                .selectable(
                    selected = isSelectedAndNotDisabled,
                    enabled = !isDisabled,
                    onClick = onClick
                ),
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isSelectedAndNotDisabled) 2.dp else 0.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp).wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ){

                val resId = if (isSelectedAndNotDisabled) R.drawable.check else when(icon) {
                    "apartment" -> R.drawable.apartment
                    "boat" -> R.drawable.boat
                    "condo" -> R.drawable.condo
                    "garage" -> R.drawable.garage
                    "garden" -> R.drawable.garden
                    "land" -> R.drawable.land
                    "no_room" -> R.drawable.no_room
                    "rooms" -> R.drawable.rooms
                    "swimming" -> R.drawable.swimming
                    else -> R.drawable.apartment
                }

                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = name,
                    modifier = modifier,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }