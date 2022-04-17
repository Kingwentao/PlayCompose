package com.example.playcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

class StateObserverAndAutoUpdate : ComponentActivity() {

    private var numbers1 by mutableStateOf(mutableListOf(1, 2, 3))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val numbers = mutableStateListOf(1, 2, 3)
        val numberMap = mutableStateMapOf(1 to "1", 2 to "2")
        setContent {
            Column {
                NumberList(numbers = numbers1)
                NumberList2(numbers = numbers)
            }
        }
    }

    @Composable
    fun NumberList(numbers: MutableList<Int>) {
        Column {
            Button(onClick = {
                // 更新列表对象才能触发Compose的重组
                numbers1 = numbers.toMutableList().apply {
                    add(numbers.last() + 1)
                }
            }) {
                Text("点我：+1")
            }
            for (num in numbers) {
                Text("number: $num")
            }
        }
    }

    @Composable
    fun NumberList2(numbers: MutableList<Int>) {
        Column {
            Button(onClick = {
                numbers.add(numbers.last() + 1)
            }) {
                Text("点我2：+1")
            }
            for (num in numbers) {
                Text("number: $num")
            }
        }
    }
}
