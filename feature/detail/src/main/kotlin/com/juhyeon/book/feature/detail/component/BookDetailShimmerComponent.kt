package com.juhyeon.book.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.system.shimmer.BasicShimmer

@Composable
internal fun BookDetailShimmerComponent() {
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
            BasicShimmer(
                modifier = Modifier.size(height = 24.dp, width = 100.dp),
                shape = RoundedCornerShape(6.dp)
            )

            BasicShimmer(
                modifier = Modifier.size(34.dp),
                shape = RoundedCornerShape(6.dp)
            )
        }

        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BasicShimmer(
                modifier = Modifier.aspectRatio(3 / 4f),
                shape = RoundedCornerShape(8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 100.dp),
                    shape = RoundedCornerShape(6.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 80.dp),
                    shape = RoundedCornerShape(6.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 90.dp),
                    shape = RoundedCornerShape(6.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 140.dp),
                    shape = RoundedCornerShape(6.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 120.dp),
                    shape = RoundedCornerShape(6.dp)
                )
                BasicShimmer(
                    modifier = Modifier.size(height = 16.dp, width = 100.dp),
                    shape = RoundedCornerShape(6.dp)
                )
            }
        }

        BasicShimmer(
            modifier = Modifier.size(height = 18.dp, width = 70.dp),
            shape = RoundedCornerShape(6.dp)
        )

        BasicShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailShimmerComponentPreview() {
    BookDetailShimmerComponent()
}