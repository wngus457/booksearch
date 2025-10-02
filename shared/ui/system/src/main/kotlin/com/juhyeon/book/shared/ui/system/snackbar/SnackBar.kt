package com.juhyeon.book.shared.ui.system.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
fun BasicSnackBar(
    state: SnackbarHostState,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Black,
    contentColor: Color = Color.White
) {
    SnackbarHost(hostState = state, modifier = modifier) {
        CustomSnackBar(
            message = it.visuals.message,
            contentColor = contentColor,
            containerColor = containerColor
        )
    }
}

@Composable
private fun CustomSnackBar(
    message: String,
    contentColor: Color,
    containerColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(containerColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.normal(14),
            color = contentColor
        )
    }
}