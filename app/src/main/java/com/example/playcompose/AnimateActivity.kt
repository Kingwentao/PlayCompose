package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AnimateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var big by mutableStateOf(false)
        setContent {
            // note：size不能定义成var的原因，animateDpAsState是个State而不是MutableState，只能读不能写
            val size by animateDpAsState(if (big) 96.dp else 48.dp)
            // Animatable 与 animateDpAsState 的区别：
            // animateDpAsState是对Animation 的封装，更简便的使用动画，抛弃了一些Animatable的功能
            // 如果只是对状态简单的转移，animateDpAsState 就可以满足了，如果要对过程进行定制，则需要Animatable
            val anim = remember { Animatable(48.dp, Dp.VectorConverter) }
            // 普通的协程：不支持重组优化，重组过程会重复执行
//            lifecycleScope.launch {
//                // 瞬间移动目标值
//                anim.snapTo(200.dp)
//                // 动画的方式慢慢到目标值
//                anim.animateTo(96.dp)
//            }
            // 使用Compose提供的协程
            LaunchedEffect(big) {
                // 瞬间移动目标值
                anim.snapTo(200.dp)
                // 动画的方式慢慢到目标值
                anim.animateTo(96.dp)
            }
            Box(
                Modifier
                    .size(anim.value)
                    .background(Color.Red)
                    .clickable {
                        big = !big
                    })
        }
    }


}