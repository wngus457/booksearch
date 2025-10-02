package com.juhyeon.book.shared.ui.system.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.shared.ui.extension.clickableSingle
import com.juhyeon.book.shared.ui.extension.clickableSingleIgnoreInteraction
import com.juhyeon.book.shared.ui.system.icon.BottomNavSelectedBookmark
import com.juhyeon.book.shared.ui.system.icon.BottomNavUnSelectedBookmark
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.bold
import com.juhyeon.book.shared.ui.system.theme.normal
import com.juhyeon.book.shared.ui.system.theme.semiBold
import com.juhyeon.book.shared.util.extension.applyCommaFormat

@Composable
fun BookItemComponent(
    item: Book,
    isBookmark: Boolean,
    onClick: () -> Unit,
    onChangeBookmark: (Boolean, Book) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Palette.Common100)
            .clickableSingle { onClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(3 / 4f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Palette.Neutral80),
                model = item.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "도서",
                    style = MaterialTheme.typography.normal(10)
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bold(14),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "출판사 : ${item.publisher}",
                    style = MaterialTheme.typography.normal(12),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "저자 : ${item.authors.firstOrNull() ?: ""}",
                    style = MaterialTheme.typography.normal(12),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickableSingleIgnoreInteraction { onChangeBookmark(!isBookmark, item) },
                painter = painterResource(if (isBookmark) BottomNavSelectedBookmark else BottomNavUnSelectedBookmark),
                contentDescription = null,
                tint = Palette.Orange50
            )

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${item.salePrice.applyCommaFormat()}원",
                    textAlign = TextAlign.End,
                    color = Palette.Neutral70,
                    textDecoration = TextDecoration.LineThrough,
                    style = MaterialTheme.typography.normal(12, 18)
                )

                Text(
                    text = "${item.price.applyCommaFormat()}원",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.semiBold(16),
                    color = Palette.Neutral10
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookItemComponentPreview() {
    BookItemComponent(
        item = Book(
            id = "fdfsdf9000",
            title = "테스트",
            contents = "테스트컨텐츠",
            url = "",
            isbn = "fdfsdf",
            datetime = "2025-08-15T00:00:00.000+09:00",
            authors = listOf(),
            publisher = "길잡이",
            translators = listOf(),
            price = 10000,
            salePrice = 9000,
            thumbnail = "",
            status = "정상판매"
        ),
        isBookmark = false,
        onClick = { },
        onChangeBookmark = { _, _ -> }
    )
}