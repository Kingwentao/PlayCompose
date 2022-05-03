package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class DerivedStateOfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // example1：简单的变量，带参数的remember，也可以实现依赖变化的变量更新
//            var age by remember { mutableStateOf(1) }
//            val person by remember{
//                derivedStateOf { "my age is $age" }
//            }
////             参数的意义：如果参数变化了，就重新执行内部代码
//            val person = remember(age) {
//                "my age is $age"
//            }
//
//            Column {
//                Button(onClick = {
//                    age += 1
//                }) {
//                    Text(text = "click add age")
//                }
//                Text(text = person)
//            }


            // example2：内部元素变化的list
            val nameList = remember {
                mutableStateListOf("张三")
            }
            // nameList内部元素变化，不会触发personList的改变
            val personList = remember(nameList) {
                nameList.map {
                    "my name is $it"
                }
            }
//             updateNameList(nameList = nameList, personList)
            // nameList内部元素变化，可以触发personList的改变
            val personList2 by remember {
                derivedStateOf {
                    nameList.map {
                        "my name is $it"
                    }
                }
            }
            updateNameList(nameList = nameList, personList2)

        }
    }

    @Composable
    fun updateNameList(nameList: MutableList<String>, personList: List<String>) {
        Column {
            Button(onClick = {
                nameList.add("李四")
            }) {
                Text(text = "add new name")
            }
            for (person in personList) {
                Text(text = person)
            }
        }
    }

}