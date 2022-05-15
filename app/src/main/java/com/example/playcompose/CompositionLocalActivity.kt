package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView

class CompositionLocalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                CompositionLocalProvider(LocalColor provides Color.Blue) {
                    LocalColorText()
                }

                CompositionLocalProvider(LocalColor provides Color.Green) {
                    LocalColorText()
                }

                // Material更改主题
                val colors = darkColors()
                MaterialTheme(colors = colors) {
                    Button(onClick = { }) {
                        Text("对比按钮的颜色和colors里的background")
                    }
                }

            }
        }
    }

    @Composable
    fun LocalColorText() {
        Text("我是什么颜色的", Modifier.background(LocalColor.current))
    }

}

// 精准订阅、值改变时精准 Recompose，所以性能的消耗在于订阅阶段，适合值可能会经常改变的场景
val LocalColor = compositionLocalOf { Color.Black }

// staticCompositionLocalOf: 不支持订阅、值改变时全量 Recompose，所以性能的消耗在于更新阶段，适合值不会改变或⼏乎不会改变的场景
//val LocalColor = staticCompositionLocalOf { Color.Black }

//val LocalColor = compositionLocalOf { error("LocalColor 没有提供值！") }