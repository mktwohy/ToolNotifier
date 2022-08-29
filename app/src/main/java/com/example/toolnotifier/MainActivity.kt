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
import com.example.toolnotifier.ui.theme.ToolNotifierTheme

private const val LAST_UPDATED_FILENAME = "LastUpdated.txt"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isWebsiteUpdated by mutableStateOf(false)

            ToolNotifierTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { isWebsiteUpdated = checkForUpdate() }
                        ) {
                            Text(text = "Manually Check for Update")
                        }
                        Text("Is Website Updated: $isWebsiteUpdated")
                        Text("Last Updated: ${ readTextFile(LAST_UPDATED_FILENAME)?.removePrefix("Last Updated ") }")
                    }
                }
            }
        }
    }

    fun checkForUpdate(): Boolean {
        val previousDate: String? = readTextFile(LAST_UPDATED_FILENAME)
        val currentDate: String = getLastUpdatedDate()

        if (previousDate == null) {
            Log.i("File does not exist. Initializing file...")
            writeTextFile(LAST_UPDATED_FILENAME, currentDate)
            return false
        }

        return previousDate != currentDate
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