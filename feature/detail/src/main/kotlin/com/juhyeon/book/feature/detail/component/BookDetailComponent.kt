package com.juhyeon.book.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.shared.ui.extension.clickableSingle
import com.juhyeon.book.shared.ui.system.icon.BottomNavSelectedBookmark
import com.juhyeon.book.shared.ui.system.icon.BottomNavUnSelectedBookmark
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.bold
import com.juhyeon.book.shared.ui.system.theme.normal
import com.juhyeon.book.shared.util.extension.applyCommaFormat
import com.juhyeon.book.shared.util.extension.iso8601ToFormat

@Composable
internal fun BookDetailComponent(
    book: Book,
    isBookmark: Boolean,
    onChangeBookmark: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 20.dp, bottom = 0.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = book.title,
                style = MaterialTheme.typography.bold(24)
            )

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clickableSingle { onChangeBookmark() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(34.dp),
                    painter = painterResource(if (isBookmark) BottomNavSelectedBookmark else BottomNavUnSelectedBookmark),
                    contentDescription = "",
                    tint = Palette.Orange50
                )
            }
        }

        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(3 / 4f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Palette.Neutral80),
                model = book.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                BookInfoItem(
                    title = "저자",
                    value = book.authors.joinToString(",")
                )
                BookInfoItem(
                    title = "출판사",
                    value = book.publisher
                )
                BookInfoItem(
                    title = "출간일",
                    value = book.datetime.iso8601ToFormat()
                )
                BookInfoItem(
                    title = "ISBN",
                    value = book.isbn.split(" ").joinToString(", ")
                )
                BookInfoItem(
                    title = "정상가",
                    value = "${book.price.applyCommaFormat()}원"
                )
                BookInfoItem(
                    title = "할인가",
                    value = "${book.salePrice.applyCommaFormat()}원"
                )
            }
        }

        Text(
            text = "책 소개",
            style = MaterialTheme.typography.bold(18)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Palette.Neutral80)
                .padding(12.dp)
        ) {
            Text(
                text = book.contents,
                style = MaterialTheme.typography.normal(14)
            )
        }
    }
}

@Composable
private fun BookInfoItem(
    title: String,
    value: String
) {
    Row {
        Text(
            text = "$title : ",
            style = MaterialTheme.typography.bold(16)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.normal(16)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailComponentPreview() {
    BookDetailComponent(
        book = Book(
            id = "fdfsdf9000",
            title = "테스트",
            contents = "테스트컨텐츠",
            url = "",
            isbn = "fdfsdf aaaaa",
            datetime = "2025-08-15T00:00:00.000+09:00",
            authors = listOf("저자"),
            publisher = "길잡이",
            translators = listOf(),
            price = 10000,
            salePrice = 9000,
            thumbnail = "",
            status = "정상판매"
        ),
        isBookmark = false,
        onChangeBookmark = { }
    )
}