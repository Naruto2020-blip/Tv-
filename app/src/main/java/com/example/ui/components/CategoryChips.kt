package com.example.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChannelCategory
import com.example.ui.theme.BorderColor
import com.example.ui.theme.CardBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun CategoryChips(
    selectedCategory: ChannelCategory,
    onSelectCategory: (ChannelCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChannelCategory.values().forEach { category ->
            val isSelected = selectedCategory == category

            FilterChip(
                selected = isSelected,
                onClick = { onSelectCategory(category) },
                label = {
                    Text(
                        text = category.displayName,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                shape = RoundedCornerShape(18.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = CardBackground,
                    labelColor = TextMuted,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = BorderColor,
                    selectedBorderColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.testTag("category_chip_${category.name.lowercase()}")
            )
        }
    }
}
