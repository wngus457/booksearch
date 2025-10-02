package com.juhyeon.book.shared.ui.system.sort

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.extension.clickableSingle
import com.juhyeon.book.shared.ui.system.dropdown.BasicDropdownMenu
import com.juhyeon.book.shared.ui.system.icon.CommonSort
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.bold
import com.juhyeon.book.shared.ui.system.theme.medium
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
fun <T : BasicSortItems> RowScope.BasicSort(
    title: String,
    icon: ImageVector = CommonSort,
    list: List<T>,
    selectedSort: T,
    onChangeFilter: (T) -> Unit
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.End
    ) {
        var expanded by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .height(34.dp)
                .border(
                    width = 1.dp,
                    color = Palette.Neutral80,
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .clickableSingle { expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                tint = Palette.Neutral55,
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = title,
                style = MaterialTheme.typography.normal(14)
            )
        }

        BasicDropdownMenu(
            modifier = Modifier
                .padding(top = 4.dp)
                .background(Palette.Common100)
                .border(1.dp, color = Palette.Neutral85, shape = RoundedCornerShape(6.dp)),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                list.forEach {
                    Text(
                        modifier = Modifier.clickableSingle {
                            expanded = false
                            onChangeFilter(it)
                        },
                        text = it.value,
                        style = if (it.key == selectedSort.key) MaterialTheme.typography.bold(14) else MaterialTheme.typography.medium(14),
                        color = if (it.key == selectedSort.key) Palette.Green50 else Palette.Neutral35
                    )
                }
            }
        }
    }
}