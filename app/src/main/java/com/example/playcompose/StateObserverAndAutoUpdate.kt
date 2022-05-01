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

    //    var user by mutableStateOf(User("wtk"))
    var clickText by mutableStateOf("点击前")
    var user = User("张三")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val numbers = mutableStateListOf(1, 2, 3)
        val numberMap = mutableStateMapOf(1 to "1", 2 to "2")


        setContent {
            var text by remember { mutableStateOf("未点击") }
            Column {
//                NumberList(numbers = numbers1)
//                NumberList2(numbers = numbers)

                // 测试：重组的优化
//                Button(onClick = {
//                    text = "已点击"
//                }) {
//                    Text(text = "点我更新内容")
//                }
//                Text(text = "内容：$text")
//                println("111")
//                RecomposeOptimization()
//                println("333")

                // 引用对象的重组优化有效性
                println("000")
                Button(onClick = {
                    clickText = "点击后"
                    user.name = "张三"
                }) {
                    Text(clickText)
                }
                println("111：$clickText")
                UpdateUser(user)
                println("333")
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

    @Composable
    fun UpdateUser(user: User) {
        println("222 user name is： ${user.name}")
        Text(text = "name: " + user.name)
    }

    class User(name: String) {
        var name by mutableStateOf(name)
    }

    @Composable
    fun RecomposeOptimization() {
        println("222")
    }

}