package com.example.playcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
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
        // mutableStateOf具备订阅功能，内部会去取值
//        var name by mutableStateOf("compose")

        // 使用by委托需要手动import androidx.compose.runtime.getValue/setValue，目前不支持自动导入
        setContent {
            nextActivity()
//            getNormalView()
//            Text(name)
            
              // remember
//            UseRemember()
//            val name by remember {
//                mutableStateOf("compose")
//            }
              // stateless
//            Stateless(name)
            //
//            var value by remember {
//                mutableStateOf("abc")
//            }
//            materialUI(value = value, onValueChange =  { newValue ->
//                value = newValue
//            })
        }

    }

    @Composable
    private fun nextActivity() {
        Column{
            Button(onClick = {
                Log.d("", "button clicked.")
                startActivity(Intent(this@MainActivity, StateObserverAndAutoUpdate::class.java))
            }){
                Text("状态订阅和自动更新")
            }
        }
    }

    /**
     * 重组过程：输入更改时再次调用可组合函数的过程（Recompose过程）
     * remember可以避免二次执行时，变量的重新初始化，它会去拿之前缓存的值
     */
    @Composable
    fun UseRemember() {
        var name by remember {
            mutableStateOf("compose")
        }
        Text("name is $name")
        Log.d(TAG, "UseRemember: ")
        lifecycleScope.launch {
            delay(3000)
            name = "Recompose"
        }
    }


    @Composable
    fun Stateless(name: String) {
        Text("name is $name")
    }
    
    @Composable
    fun materialUI(value: String, onValueChange: (String) -> Unit){
        TextField(value = value, onValueChange = onValueChange)
    }
}