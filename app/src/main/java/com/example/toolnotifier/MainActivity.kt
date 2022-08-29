package com.example.toolnotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toolnotifier.extensions.viewModel
import com.example.toolnotifier.ui.theme.ToolNotifierTheme


class MainActivity : ComponentActivity() {
    val viewModel by lazy { viewModel<MainActivityViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextHolder.init(this)

        setContent {
            ToolNotifierTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = viewModel::checkForUpdate) {
                            Text(text = "Manually Check for Update")
                        }
                        Text(viewModel.lastUpdatedDateText)
                        Text(viewModel.didWebsiteUpdateText)
                    }
                }
            }
        }
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToolNotifierTheme {
        Greeting("Android")
    }
}