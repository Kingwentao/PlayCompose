package com.example.playcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.example.playcompose.ui.getNormalView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用by委托需要手动import androidx.compose.runtime.getValue/setValue，目前不支持自动导入
        var name by mutableStateOf("composable")
        setContent {
//            getNormalView()
            // mutableStateOf具备订阅功能，内部会去取值
            Text(name)
        }
        lifecycleScope.launch {
            delay(3000)
            name = "composable 3000"
        }
    }

}