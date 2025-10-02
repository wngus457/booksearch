package com.juhyeon.book.feature.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.system.sort.BasicSort
import com.juhyeon.book.shared.ui.system.sort.SortSearch
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
internal fun SearchSortComponent(
    selectedSort: SortSearch,
    onChangeSort: (SortSearch) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedSort.value,
            style = MaterialTheme.typography.normal(14)
        )
        BasicSort(
            title = "정렬",
            list = SortSearch.entries,
            selectedSort = selectedSort,
            onChangeFilter = onChangeSort
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchSortComponentPreview() {
    SearchSortComponent(
        selectedSort = SortSearch.ACCURACY,
        onChangeSort = { }
    )
}