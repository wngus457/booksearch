package com.juhyeon.book.feature.bookmark.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.bold

@Composable
internal fun BookmarkEmptyComponent() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "즐겨찾기 목록이 없습니다.\n검색 목록에서 즐겨찾기를 추가해보세요!",
        style = MaterialTheme.typography.bold(20),
        textAlign = TextAlign.Center,
        color = Palette.Neutral30
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewBookmarkEmptyComponent() {
    BookmarkEmptyComponent()
}