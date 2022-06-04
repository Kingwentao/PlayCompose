package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

class StopAnimateActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "StopAnimateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val anim = remember { Animatable(0.dp, Dp.VectorConverter) }
            val exponentDecay = exponentialDecay<Dp>()
            LaunchedEffect(key1 = Unit) {
                delay(1000)
                try {
                    val result = anim.animateDecay(2000.dp, exponentDecay)
                    Log.d(TAG, "onCreate anim end reason: ${result.endReason}")
                } catch (e: CancellationException) {
                    Log.e(TAG, "anim is CancellationException")
                    e.printStackTrace()
                }
            }

//            LaunchedEffect(key1 = Unit) {
//                delay(1300)
//                anim.stop()
//            }

            // 更新边界，到达边界自动停止
            anim.updateBounds(upperBound = 200.dp)

            Box(
                Modifier
                    .padding(0.dp, anim.value, 0.dp, 0.dp)  // 先padding后background：margin的效果
                    .size(100.dp)
                    .background(Color.Red)
            )
        }
    }
}