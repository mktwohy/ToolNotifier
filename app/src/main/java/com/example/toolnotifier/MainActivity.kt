package com.example.toolnotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.*
import com.example.toolnotifier.extensions.WorkManagerInstance
import com.example.toolnotifier.extensions.viewModel
import com.example.toolnotifier.ui.theme.ToolNotifierTheme
import com.example.toolnotifier.util.ContextHolder
import com.example.toolnotifier.workmanager.UpdateDateWorker
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    private val viewModel by lazy { viewModel<MainActivityViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextHolder.init(context = this)
        initWorkManager()

        setContent {
            ToolNotifierTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = viewModel::manuallyCheckForUpdate,
                            enabled = !viewModel.isUpdating
                        ) {
                            Text(text = "Manually Check for New Tools")
                        }

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(50.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (viewModel.isUpdating) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(25.dp),
                                    strokeWidth = 3.dp
                                )
                            } else {
                                Column {
                                    Text(text = viewModel.websiteUpdatedDate)
                                    Text(text = viewModel.lastCheckedDate)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initWorkManager() {
        WorkManagerInstance.cancelAllWork()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateDateWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<UpdateDateWorker>(30L, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManagerInstance.enqueueUniquePeriodicWork(
            "UpdateDateWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            updateDateWorkRequest
        )
    }

}
