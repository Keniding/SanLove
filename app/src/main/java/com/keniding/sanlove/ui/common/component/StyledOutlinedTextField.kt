package com.keniding.sanlove.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keniding.sanlove.ui.common.theme.ValentineColors

@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ValentineColors.DeepRose,
            unfocusedBorderColor = ValentineColors.Rose.copy(alpha = 0.6f),
            focusedLabelColor = ValentineColors.DeepRose,
            unfocusedLabelColor = ValentineColors.Rose.copy(alpha = 0.8f),
            focusedContainerColor = ValentineColors.WarmWhite.copy(alpha = 0.9f),
            unfocusedContainerColor = ValentineColors.WarmWhite.copy(alpha = 0.8f)
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = ValentineColors.DeepRose
        )
    )
}

