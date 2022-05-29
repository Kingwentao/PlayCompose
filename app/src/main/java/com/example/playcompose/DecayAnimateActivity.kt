package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class AnimateDecayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val anim = remember { Animatable(0.dp, Dp.VectorConverter) }

            var padding by remember { mutableStateOf(anim.value) }

            // 基于样条的衰减: 用于像素单位的衰减
            val splineBasedDecay = rememberSplineBasedDecay<Dp>()
            // 指数式衰减：适用范围广，旋转
            val exponentDecay = exponentialDecay<Dp>()
            LaunchedEffect(key1 = Unit) {
                delay(1000)
                // 以每秒1000dp的速度
//                anim.animateDecay(1000.dp, splineBasedDecay)
                anim.animateDecay(1000.dp, exponentDecay, block = {
                    Log.d("AnimateDecay", "animate block: $value")
                    // 目标：跟随红色的动画过程移动
                    padding = value
                })
            }
            Row {
                Box(
                    Modifier
                        .padding(
                            0.dp,
                            top = anim.value,
                            0.dp,
                            0.dp
                        )  // 先padding后background：margin的效果
                        .size(100.dp)
                        .background(Color.Red)
                )

                Box(
                    Modifier
                        .padding(
                            0.dp,
                            top = padding,
                            0.dp,
                            0.dp
                        )  // 先padding后background：margin的效果
                        .size(100.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}