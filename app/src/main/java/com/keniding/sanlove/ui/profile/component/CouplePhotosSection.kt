package com.keniding.sanlove.ui.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard

@Composable
fun CouplePhotosSection(partner1: Partner, partner2: Partner) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PartnerPhoto(partner = partner1)
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = ValentineColors.DeepRose,
                modifier = Modifier.size(32.dp)
            )
            PartnerPhoto(partner = partner2)
        }
    }
}