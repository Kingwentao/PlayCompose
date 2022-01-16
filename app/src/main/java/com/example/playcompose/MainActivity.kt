package com.example.playcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 位图： Bitmap，用像素表达图片，但在Compose用ImageBitmap表示，为什么要改名字呢？
            // 独立于Android平台，不被Android系统版本依赖，支持多平台，比如实现AS的预览功能，桌面版、Web，
            // 但没有ios，而是先开发桌面版的支持，因为JetBrain目标初衷是为了推广Kotlin

//            // Row Column => LinearLayout
//            Row {
//                Text(text = "LinearLayout and Row")
//                Image(painterResource(id = R.drawable.ic_launcher_background), "icon")
//            }
//            Column {
//                Text(text = "LinearLayout and Column")
//                Image(painterResource(id = R.drawable.ic_launcher_background), "icon")
//            }
//
//            // Box => FrameLayout/RelativeLayout
//            Box{
//                Text(text = "Box and FrameLayout")
//                Image(painterResource(id = R.drawable.ic_launcher_background), "icon")
//            }

            // LazyColumn/LazyRow => Recyclerview
            val names = listOf("a", "b", "c", "a", "b", "c")
//            LazyColumn {
//                items(names) { name ->
//                    Column {
//                        Text(name)
//                        Image(painterResource(id = R.drawable.ic_launcher_background), "icon")
//                    }
//                }
//            }

//            //Modifier.verticalScroll() / Modifier.horizontalScroll() => ScrollView
//            Column(Modifier.verticalScroll(ScrollState())) {//...}

        }
    }
}