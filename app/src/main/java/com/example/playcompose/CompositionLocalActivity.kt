package com.example.playcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

class CompositionLocalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(localName provides "张三") {
                User()
            }
        }
    }

    @Composable
    fun User() {
        Text(localName.current)
    }

    private val localName = compositionLocalOf<String> {
        "default name"
    }

}