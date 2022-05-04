package com.example.playcompose

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DerivedStateOfActivity : AppCompatActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                // example1：简单的变量，带参数的remember，也可以实现依赖变化的变量更新
                var age by remember { mutableStateOf(1) }
                updatePersonAge(age = age) {
                    age += 1
                }

                // example2：内部元素变化的list
                var nameList = remember {
                    mutableStateListOf("张三")
                }
                println("111")
//            updateNameList(nameList = nameList) {
//                nameList.add("李四")
//                println("click: $nameList")
//            }
                Text("age is: $age")
                // example3: 测试替换对象+内部元素变换
                for (name in nameList) {
                    // TODO：重组时，为什么nameList对象没有还是旧的，没有替换成新的？？？
                    println("click1: object: $nameList $name")
                }
                updateNameList(nameList = nameList) {
                    // 替换整个对象，测试是否updateNameList里面是否可以更新?
                    nameList = mutableStateListOf("李四", "张三")
                }
                println("333")
            }
        }
    }

    @Composable
    fun updateNameList(nameList: List<String>, onClick: () -> Unit) {
        println("2222")
        // nameList内部元素变化，不会触发personList的改变
//        val personList = remember(nameList) {
//            nameList.map {
//                "my name is $it"
//            }
//        }
        for (name in nameList) {
            println("click2: $name")
        }
        // nameList内部元素变化，可以触发personList的改变
        // 使用带参数的remember解决： 监听nameList的变化
        val personList by remember(nameList) {
            derivedStateOf {
                nameList.map {
                    "my name is $it"
                }
            }
        }
        Column {
            Button(onClick = {
                onClick.invoke()
            }) {
                Text(text = "add new name")
            }
        }
        for (person in personList) {
            Text(text = person)
        }
    }

    @Composable
    fun updatePersonAge(age: Int, onClick: () -> Unit) {
//      参数的意义：如果参数变化了，就重新执行内部代码
        val person = remember(age) {
            "my age is $age"
        }
        Column {
            Button(onClick = {
                onClick.invoke()
            }) {
                Text(text = "click add age")
            }
            Text(text = person)
        }
    }

    @Composable
    fun updatePersonAge2(age: State<Int>, onClick: () -> Unit) {
        val person by remember {
            derivedStateOf { "my age is $age" }
        }
        Column {
            Button(onClick = {
                onClick.invoke()
            }) {
                Text(text = "click add age")
            }
            Text(text = person)
        }
    }

}