package com.juhyeon.book.shared.ui.system.navigation.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.extension.clickableSingle
import com.juhyeon.book.shared.ui.system.icon.CommonBack
import com.juhyeon.book.shared.ui.system.icon.CommonClose
import com.juhyeon.book.shared.ui.system.theme.semiBold

@Composable
private fun BasicTopNavigation(
    modifier: Modifier = Modifier,
    backType: BasicTopNavigationBackType = BasicTopNavigationBackType.Off,
    iconType: BasicTopNavigationIconType = BasicTopNavigationIconType.None,
    title: BasicTopNavigationTitle = BasicTopNavigationTitle.Off,
    onBackClick: () -> Unit = { },
    onRightClick: (String) -> Unit = { }
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backType == BasicTopNavigationBackType.On) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clickableSingle { onBackClick() }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    imageVector = CommonBack,
                    contentDescription = ""
                )
            }
        }

        when (title) {
            is BasicTopNavigationTitle.On -> {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title.title,
                    style = MaterialTheme.typography.semiBold(16),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = when (title.align) {
                        TopNavigationTitleAlign.Start -> TextAlign.Start
                        TopNavigationTitleAlign.Center -> TextAlign.Center
                    }
                )
            }
            is BasicTopNavigationTitle.Off -> {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        when (iconType) {
            is BasicTopNavigationIconType.Close -> {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clickableSingle { onRightClick("Close") }
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        imageVector = CommonClose,
                        contentDescription = ""
                    )
                }
            }
            is BasicTopNavigationIconType.Icons -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    iconType.icons.forEach { icon ->
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clickableSingle { onRightClick(icon.iconId) }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(icon.resId),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
            else -> { }
        }
    }
}

@Composable
fun TopNavigationCenterTitle(
    title: String
) {
    BasicTopNavigation(
        iconType = BasicTopNavigationIconType.None,
        title = BasicTopNavigationTitle.On(title, TopNavigationTitleAlign.Center)
    )
}

@Composable
fun TopNavigationBackTitle(
    title: String,
    onBackClick: () -> Unit
) {
    BasicTopNavigation(
        backType = BasicTopNavigationBackType.On,
        iconType = BasicTopNavigationIconType.None,
        title = BasicTopNavigationTitle.On(title),
        onBackClick = onBackClick
    )
}

@Composable
fun TopNavigationTitleClose(
    title: String,
    onCloseClick: () -> Unit
) {
    BasicTopNavigation(
        iconType = BasicTopNavigationIconType.Close,
        title = BasicTopNavigationTitle.On(title),
        onRightClick = { onCloseClick() }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopNavigationCenterTitlePreview() {
    TopNavigationCenterTitle(title = "테스트")
}

@Preview(showBackground = true)
@Composable
private fun TopNavigationBackTitlePreview() {
    TopNavigationBackTitle(
        title = "테스트",
        onBackClick = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopNavigationTitleClosePreview() {
    TopNavigationTitleClose(
        title = "테스트",
        onCloseClick = { }
    )
}