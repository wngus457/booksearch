package com.juhyeon.book.feature.search.component

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
internal fun SearchEmptyComponent() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "검색 결과가 없습니다.\n다시 검색해 주세요!",
        style = MaterialTheme.typography.bold(20),
        textAlign = TextAlign.Center,
        color = Palette.Neutral30
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchEmptyComponentPreview() {
    SearchEmptyComponent()
}