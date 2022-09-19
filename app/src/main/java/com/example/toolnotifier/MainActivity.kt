package com.example.toolnotifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.trackPipAnimationHintView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.*
import com.example.toolnotifier.businessLogic.AppSettings
import com.example.toolnotifier.extensions.WorkManagerInstance
import com.example.toolnotifier.extensions.dataStore
import com.example.toolnotifier.extensions.viewModel
import com.example.toolnotifier.ui.theme.ToolNotifierTheme
import com.example.toolnotifier.util.ContextHolder
import com.example.toolnotifier.util.Log
import com.example.toolnotifier.workmanager.UpdateDateWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    private val viewModel by lazy { viewModel<MainActivityViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextHolder.init(context = this)

        setContent {
            val appSettings by dataStore.data.collectAsState(initial = AppSettings())
            val scope = rememberCoroutineScope()

            LaunchedEffect(appSettings) {
                if (appSettings.workManagerEnabled) {
                    initWorkManager()
                } else {
                    WorkManagerInstance.cancelAllWork()
                }
            }

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

                        Row(
                            modifier = Modifier
                                .padding(vertical = 30.dp, horizontal = 20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text("Automatically Check for New Tools")
                            Switch(
                                checked = appSettings.workManagerEnabled,
                                onCheckedChange = {
                                    scope.launch { setWorkManagerEnabled(it) }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initWorkManager() {
        Log.i("init work manager")
        WorkManagerInstance.cancelAllWork()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateDateWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<UpdateDateWorker>(30L, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInitialDelay(5L, TimeUnit.SECONDS)
                .build()

        WorkManagerInstance.enqueueUniquePeriodicWork(
            "UpdateDateWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            updateDateWorkRequest
        )
    }

    private suspend fun setWorkManagerEnabled(enabled: Boolean) {
        ContextHolder.context.dataStore.updateData {
            it.copy(
                workManagerEnabled = enabled
            )
        }
    }
}
