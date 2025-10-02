package com.juhyeon.book.shared.ui.system.search

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.extension.clickableSingle
import com.juhyeon.book.shared.ui.extension.textDp
import com.juhyeon.book.shared.ui.system.icon.BottomNavSelectedSearch
import com.juhyeon.book.shared.ui.system.icon.CommonClose
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    focusState: Boolean,
    onFocused: (FocusState) -> Unit = { },
    onChangeText: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Palette.Common0.copy(0.05f))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BasicTextField(
                value = text,
                textStyle = TextStyle(fontSize = 14.textDp, fontWeight = FontWeight.Normal),
                onValueChange = onChangeText,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { onFocused(it) }
                    .onKeyEvent {
                        if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                            onSearchClick()
                        }
                        false
                    },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick() }
                ),
                decorationBox = { innerTextField ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "제목 또는 저자를 입력하세요.",
                                color = Palette.Neutral55,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.normal(14)
                            )
                        }
                        if (text.isEmpty() && focusState.not()) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = BottomNavSelectedSearch),
                                contentDescription = "",
                                tint = Palette.Neutral55
                            )
                        }
                    }

                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            if (text.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFBABABA))
                        .clickableSingle { onChangeText("") }
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(14.dp),
                        imageVector = CommonClose,
                        contentDescription = "",
                        tint = Palette.Common100
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(
        text = "",
        focusState = false,
        onFocused = { },
        onSearchClick = { },
        onChangeText = { }
    )
}