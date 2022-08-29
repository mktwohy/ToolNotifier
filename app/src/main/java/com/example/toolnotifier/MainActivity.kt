package com.example.toolnotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toolnotifier.extensions.viewModel
import com.example.toolnotifier.ui.theme.ToolNotifierTheme


class MainActivity : ComponentActivity() {
    private val viewModel by lazy { viewModel<MainActivityViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextHolder.init(this)

        setContent {
            ToolNotifierTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = viewModel::checkForUpdate,
                            enabled = !viewModel.isUpdating
                        ) {
                            Text(text = "Manually Check for New Tools")
                        }

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(30.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (viewModel.isUpdating) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(25.dp),
                                    strokeWidth = 3.dp
                                )
                            } else {
                                Text(text = viewModel.lastUpdatedDateText,)
                            }
                        }

                    }
                }
            }
        }
    }
}
