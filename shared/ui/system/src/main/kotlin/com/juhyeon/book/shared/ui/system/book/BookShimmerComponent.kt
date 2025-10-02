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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.system.shimmer.BasicShimmer
import com.juhyeon.book.shared.ui.system.theme.Palette

@Composable
fun BookShimmerComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Palette.Common100)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BasicShimmer(
                modifier = Modifier.aspectRatio(3 / 4f),
                shape = RoundedCornerShape(8.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BasicShimmer(
                    modifier = Modifier.size(height = 10.dp, width = 30.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 14.dp, width = 60.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 12.dp, width = 80.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 12.dp, width = 80.dp),
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            BasicShimmer(
                modifier = Modifier.size(32.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BasicShimmer(
                    modifier = Modifier.size(height = 12.dp, width = 70.dp),
                    shape = RoundedCornerShape(4.dp)
                )

                BasicShimmer(
                    modifier = Modifier.size(height = 14.dp, width = 80.dp),
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookShimmerComponentPreview() {
    BookShimmerComponent()
}