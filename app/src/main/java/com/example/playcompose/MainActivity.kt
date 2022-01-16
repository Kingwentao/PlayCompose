package com.example.playcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

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

//            // Modifier.verticalScroll() / Modifier.horizontalScroll() => ScrollView
//            Column(Modifier.verticalScroll(ScrollState())) {//...}

            // Modifier对顺序是敏感的，比如先调用padding、后background => margin效果
            // 先调用background，后padding => padding效果
            // Compose控件布局大小类型是wrap_content
            // 通用的设置一般都放在Modifier, 专项的设置就放着控件函数里。比如点击事件clickable，但Button控件比较特殊
            // Button就是用来点击的，所以为了方便，开发者直接给了一个onClick函数，就不需要通过Modifier了
            Column(
                Modifier
                    .padding(8.dp)
                    .background(Color.Red)
            ) {
                Text(
                    text = "LinearLayout and Column",
                    // clickable放在前面和后面的效果也是不一样的，放在前面会对后续的padding也生效
                    Modifier
                        .clickable {
                            Log.d(TAG, "onCreate: click text")
                        }
                        .background(Color.Yellow, RoundedCornerShape(3.dp))
                        .padding(10.dp)
                        , fontSize = 20.sp
                )
                Image(
                    painterResource(id = R.drawable.nb), "icon",
                    Modifier
                        .clip(CircleShape)
                        .size(250.dp)
                )
            }
            // Button
            Button(onClick = { /*TODO*/ }) { }
        }
    }
}