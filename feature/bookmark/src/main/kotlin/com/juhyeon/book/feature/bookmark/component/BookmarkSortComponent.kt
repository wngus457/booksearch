package com.juhyeon.book.feature.bookmark.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.system.icon.CommonFilter
import com.juhyeon.book.shared.ui.system.sort.BasicSort
import com.juhyeon.book.shared.ui.system.sort.FilterBookmark
import com.juhyeon.book.shared.ui.system.sort.SortBookmark
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
internal fun BookmarkSortComponent(
    selectedSort: SortBookmark,
    selectedFilter: FilterBookmark,
    onChangeSort: (SortBookmark) -> Unit,
    onChangeFilter: (FilterBookmark) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = selectedSort.value,
                style = MaterialTheme.typography.normal(14)
            )

            Text(
                text = selectedFilter.value,
                style = MaterialTheme.typography.normal(14)
            )
        }

        Row(
            modifier = Modifier.width(160.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BasicSort(
                title = "필터",
                icon = CommonFilter,
                list = FilterBookmark.entries,
                selectedSort = selectedFilter,
                onChangeFilter = onChangeFilter
            )

            BasicSort(
                title = "정렬",
                list = SortBookmark.entries,
                selectedSort = selectedSort,
                onChangeFilter = onChangeSort
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkSortComponentPreview() {
    BookmarkSortComponent(
        selectedSort = SortBookmark.ASCENDING,
        selectedFilter = FilterBookmark.ALL,
        onChangeFilter = { },
        onChangeSort = { }
    )
}