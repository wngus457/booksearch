package com.juhyeon.book.shared.ui.extension

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntSize
import com.juhyeon.book.shared.util.extension.throttleFirst
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
Scale, ripple 효과를 결합한 Modifier clickable custom
사용 방법
Modifier의 clip과 background 옵션은 clickableSingleScaleInteraction 뒤에 붙어야 정상 작동함
 */
fun Modifier.clickableSingleScaleInteraction(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    throttleDuration: Long = 1000L,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = {
            if (GlobalMultiTouchPreventer.canClick(throttleDuration)) {
                onClick()
            }
        },
        role = role,
        indication = ScaleIndicationNodeFactory,
        interactionSource = remember { MutableInteractionSource() }
    )
}

object GlobalMultiTouchPreventer {
    @Volatile
    private var lastClickTime = 0L

    @Synchronized
    fun canClick(throttleDuration: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastClickTime >= throttleDuration) {
            lastClickTime = currentTime
            true
        } else {
            false
        }
    }
}

object ScaleIndicationNodeFactory : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return ScaleIndicationNode(interactionSource)
    }

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = -1
}

private class ScaleIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {
    private val animatedScalePercent = Animatable(1f)
    private val dimOpacity = Animatable(0f)


    suspend fun animateToPressed() {
        coroutineScope.launch {
            dimOpacity.animateTo(
                targetValue = 0.3f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = CubicBezierEasing(0.65f, 0f, 0.35f, 1f)
                )
            )
        }

        animatedScalePercent.animateTo(
            targetValue = 0.98f,
            animationSpec = tween(
                durationMillis = 200,
                easing = CubicBezierEasing(0.25f, 1f, 0.5f, 1f)
            )
        )
    }

    suspend fun animateToResting() {
        coroutineScope.launch {
            dimOpacity.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = CubicBezierEasing(0.65f, 0f, 0.15f, 1f)
                )
            )
        }

        animatedScalePercent.animateTo(
            targetValue = 1f,
            animationSpec = spring(stiffness = 300f, dampingRatio = 0.6f)
        )
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToResting()
                    is PressInteraction.Cancel -> animateToResting()
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        scale(
            scale = animatedScalePercent.value
        ) {
            val layerPaint = Paint().apply {
                blendMode = BlendMode.SrcOver
            }

            drawContext.canvas.saveLayer(
                drawContext.size.toRect(),
                layerPaint
            )

            this@draw.drawContent()

            if (dimOpacity.value > 0f) {
                drawRect(
                    color = Color.Black.copy(alpha = dimOpacity.value),
                    topLeft = Offset.Zero,
                    size = size,
                    blendMode = BlendMode.SrcAtop
                )
            }
            drawContext.canvas.restore()
        }
    }
}
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    multipleEventsCutter { manager ->
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { manager.processEvent { onClick() } },
            role = role,
            indication = LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}

fun Modifier.clickableSingleIgnoreInteraction(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    multipleEventsCutter { manager ->
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { manager.processEvent { onClick() } },
            role = role,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = { focusManager.clearFocus() })
    }
}


interface MultipleEventCutterManager {
    fun processEvent(event: () -> Unit)
}

@Composable
fun <T> multipleEventsCutter(
    content: @Composable (MultipleEventCutterManager) -> T
): T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content(
        object : MultipleEventCutterManager {
            override fun processEvent(event: () -> Unit) {
                debounceState.tryEmit(event)
            }
        }
    )

    LaunchedEffect(true) {
        debounceState
            .throttleFirst(650L)
            .collect { onClick ->
                onClick.invoke()
            }
    }

    return result
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        ),
        label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFE9E9E9),
                Color(0xFFF2F2F2),
                Color(0xFFE9E9E9)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}