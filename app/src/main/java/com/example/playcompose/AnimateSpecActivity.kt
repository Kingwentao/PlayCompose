package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AnimateSpecActivity : AppCompatActivity() {

    companion object {
        private const val ANIMATION_TWEEN_SPEC = 0
        private const val ANIMATION_SNAP_SPEC = 1
        private const val ANIMATION_KEY_FRAME = 2
        private const val ANIMATION_SPRING = 3
        private const val ANIMATION_REPEAT = 4
        private const val ANIMATION_INFINITE_REPEAT = 5
    }

    private var big by mutableStateOf(false)

    private var mAnimationType by mutableStateOf(ANIMATION_SPRING)

    private var mEasingMode: Easing = LinearEasing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // note：size不能定义成var的原因，animateDpAsState是个State而不是MutableState，只能读不能写
            val size by animateDpAsState(if (big) 96.dp else 48.dp)
            // Animatable 与 animateDpAsState 的区别：
            // animateDpAsState是对Animation 的封装，更简便的使用动画，抛弃了一些Animatable的功能
            // 如果只是对状态简单的转移，animateDpAsState 就可以满足了，如果要对过程进行定制，则需要Animatable
            val anim = remember { Animatable(50.dp, Dp.VectorConverter) }
            // 普通的协程：不支持重组优化，重组过程会重复执行
//            lifecycleScope.launch {
//                // 瞬间移动目标值
//                anim.snapTo(200.dp)
//                // 动画的方式慢慢到目标值
//                anim.animateTo(96.dp)
//            }

/*//             使用Compose提供的协程
            LaunchedEffect(big) {
                // 瞬间移动目标值
                anim.snapTo(200.dp)
                // 动画的方式慢慢到目标值
                anim.animateTo(96.dp, TweenSpec(easing = LinearEasing))
            }
            Box(
                Modifier
                    .size(anim.value)
                    .background(Color.Red)
                    .clickable {
                        big = !big
                    })*/

            //2.spec
            Column {
                if (mAnimationType == ANIMATION_TWEEN_SPEC) {
                    NormalTweenSpec()
                } else {
                    AnimationType()
                }
                LaunchedEffect(mAnimationType, big) {
                    executeAnimate(anim)
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

    private suspend fun executeAnimate(anim: Animatable<Dp, AnimationVector1D>) {
        when (mAnimationType) {
            ANIMATION_TWEEN_SPEC -> {
                // 动画的方式慢慢到目标值
                anim.animateTo(
                    if (big) 250.dp else 50.dp,
                    TweenSpec(easing = mEasingMode, durationMillis = 2000)
                )
            }
            ANIMATION_SNAP_SPEC -> {
                // 动画的方式瞬间到目标值
                anim.animateTo(
                    if (big) 250.dp else 50.dp,
                    SnapSpec()
                )
            }
            ANIMATION_SPRING -> {
                // 设置弹簧效果: 阻尼比（控制弹力大小，值越小弹力越大，默认是1，无弹力）, 刚性（弹簧的硬度，越硬越弹不动），可见阈值（到某个值停止弹动）
                anim.animateTo(
                    if (big) 250.dp else 50.dp,
                    spring(Spring.DampingRatioHighBouncy, Spring.StiffnessLow, null)
                )
            }
            ANIMATION_KEY_FRAME -> {
                // 设置动画关键帧
                anim.animateTo(if (big) 250.dp else 50.dp,
                    keyframes {
                        durationMillis = 1200
                        50.dp at 0 with LinearEasing
                        100.dp at 500 with FastOutSlowInEasing
                        250.dp at 1000
                    }
                )
            }
            ANIMATION_REPEAT -> {
                anim.animateTo(
                    250.dp,
                    repeatable(
                        3,
                        TweenSpec(easing = mEasingMode, durationMillis = 2000),
                        RepeatMode.Reverse,
                        StartOffset(100, StartOffsetType.FastForward)
                    )
                )
            }
            ANIMATION_INFINITE_REPEAT -> {
                anim.animateTo(
                    250.dp,
                    infiniteRepeatable(
                        TweenSpec(easing = mEasingMode, durationMillis = 2000),
                        RepeatMode.Reverse,
                        StartOffset(100, StartOffsetType.FastForward)
                    )
                )
            }
        }
    }

    @Composable
    fun NormalTweenSpec() {
        Button(onClick = {
            mEasingMode = LinearEasing
            big = !big
        }) {
            Text("LinearEasing(匀速)")
        }
        Button(onClick = {
            mEasingMode = FastOutSlowInEasing
            big = !big
        }) {
            Text("FastOutSlowInEasing（先加速再减速）")

        }
        Button(onClick = {
            mEasingMode = LinearOutSlowInEasing
            big = !big
        }) {
            Text("LinearOutSlowInEasing（减速入场）")

        }
        Button(onClick = {
            mEasingMode = FastOutLinearInEasing
            big = !big
        }) {
            Text("FastOutLinearInEasing（加速出场）")
        }
        Button(onClick = {
            mEasingMode = CubicBezierEasing(0f, 0.45f, 0.45f, 1.0f)
            big = !big
        }) {
            Text("自定义的三阶贝塞尔曲线")
        }
    }

    @Composable
    fun AnimationType() {
        Button(onClick = {
            mAnimationType = ANIMATION_TWEEN_SPEC
        }) {
            Text("ANIMATION_TWEEN_SPEC(缓动动画)")
        }

        Button(onClick = {
            mAnimationType = ANIMATION_SNAP_SPEC
        }) {
            Text("ANIMATION_SNAP_SPEC(快速到达)")
        }


        Button(onClick = {
            mAnimationType = ANIMATION_SPRING
        }) {
            Text("ANIMATION_SPRING(弹簧效果)")
        }

        Button(onClick = {
            mAnimationType = ANIMATION_KEY_FRAME
        }) {
            Text("ANIMATION_KEY_FRAME(关键帧)")
        }

        Button(onClick = {
            mAnimationType = ANIMATION_REPEAT
        }) {
            Text("ANIMATION_REPEAT（多次重复）")
        }

        Button(onClick = {
            mAnimationType = ANIMATION_INFINITE_REPEAT
        }) {
            Text("ANIMATION_INFINITE_REPEAT（无限次重复）")
        }
    }

}