package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

class TransitionActivity : AppCompatActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var visible by remember {
                mutableStateOf(false)
            }
            Column {
                // 淡入使用fadeIn，initialAlpha: 初始透明度
                AnimatedVisibility(visible = visible, enter = fadeIn(initialAlpha = 0.2f)) {
                    BoxView("淡入入场")
                }

                // 滑动入场使用slideIn，initialOffset：设置出场位置
                AnimatedVisibility(
                    visible = visible,
                    enter = slideIn(initialOffset = { IntOffset(-it.width, -it.height) })
                ) {
                    BoxView("滑动入场")
                }

                // 展开，裁剪效果入场，expandFrom: 展开的方向,initialSize: 展开的初始位置，clip: 是否裁剪，默认是true
                AnimatedVisibility(
                    visible = visible,
                    enter = expandIn(
                        tween(5000),
                        expandFrom = Alignment.TopStart,
                        initialSize = { IntSize(0, 0) },
                        clip = true
                    )
                ) {
                    BoxView("裁剪入场")
                }

                // 缩放入场：initialScale：初次缩放值得，transformOrigin: 设置缩放中心，默认是中心
                AnimatedVisibility(
                    visible = visible,
                    enter = scaleIn(
                        initialScale = 0f, transformOrigin = TransformOrigin(0f, 0f)
                    )
                ) {
                    BoxView("缩放入场")
                }

                // 叠加效果：淡入+裁剪展开
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(5000), initialAlpha = 0.1f) + expandIn(
                        tween(3000),
                        expandFrom = Alignment.TopStart
                    )
                ) {
                    BoxView("淡入+裁剪效果入场")
                }

                Button(onClick = {
                    visible = !visible
                }) {
                    Text("执行入场动画")
                }
            }
//            TransitionExample2()
            // 实现两个控件切换的淡入淡出效果
//            CrossFade()
        }
    }

    @Composable
    fun BoxView(desc: String) {
        Row {
            Box(
                Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Green)
                    .clickable {
                    })
            Text(text = "------------> $desc")
        }
    }

    @Composable
    @Preview
    fun TransitionExample() {
        // 设定当前的状态
        var curBoxState by remember { mutableStateOf(BoxState.SMALL) }
        // 创建/更新Transition
        val transition = updateTransition(targetState = curBoxState, label = "BoxState")
        // 大小根据状态变化
        val size by transition.animateDp(label = "size") {
            if (it == BoxState.BIG) 96.dp else 48.dp
        }
        // 透明度根据状态变化
        val alpha by transition.animateFloat(label = "alpha") {
            if (it == BoxState.BIG) 0.1f else 0.9f
        }
        Box(
            Modifier
                .size(size)
                .alpha(alpha)
                .background(Color.Green)
                .clickable {
                    curBoxState = if (curBoxState == BoxState.SMALL) BoxState.BIG
                    else BoxState.SMALL
                })
    }

    @Composable
    @Preview
    fun TransitionExample2() {
        var curBoxState by remember { mutableStateOf(BoxState.BIG) }
        // 设定初始状态
        // TODO: 如何应用这个初始状态？
        val curBoxTransitionState = remember { MutableTransitionState(BoxState.SMALL) }
        // 设定目标状态
        curBoxTransitionState.targetState = curBoxState
        // 创建/更新Transition
        val transition = updateTransition(targetState = curBoxTransitionState, label = "BoxState")
        // 大小根据状态变化
        val size by transition.animateDp(
            {
                if (curBoxTransitionState.currentState == BoxState.SMALL
                    && curBoxTransitionState.targetState == BoxState.BIG
                ) {
                    tween(2000)
                } else spring()
            },
            label = "size"
        ) {
            if (it.targetState == BoxState.BIG) 200.dp else 48.dp
        }
        // 透明度根据状态变化
        val alpha by transition.animateFloat(label = "alpha") {
            if (it.targetState == BoxState.BIG) 0.1f else 0.9f
        }
        Box(
            Modifier
                .size(size)
                .alpha(alpha)
                .background(Color.Green)
                .clickable {
                    curBoxState =
                        if (curBoxState == BoxState.SMALL) {
                            BoxState.BIG
                        } else {
                            BoxState.SMALL
                        }
                })
    }

    /**
     * 实现两个控件切换的淡入淡出效果
     */
    @Composable
    fun CrossFade() {
        Column {
            var curBoxState by remember { mutableStateOf(BoxState.SMALL) }
            Crossfade(targetState = curBoxState) {
                when (it) {
                    BoxState.SMALL -> {
                        Box(
                            Modifier
                                .size(20.dp)
                                .background(Color.Green)
                        )
                    }
                    BoxState.BIG -> {
                        Box(
                            Modifier
                                .size(40.dp)
                                .background(Color.Red)
                        )
                    }
                }
            }
            Button(onClick = {
                curBoxState = if (curBoxState == BoxState.SMALL) {
                    BoxState.BIG
                } else {
                    BoxState.SMALL
                }
            }) {
                Text(text = "切换")
            }
        }
    }

    enum class BoxState {
        SMALL, BIG
    }
}