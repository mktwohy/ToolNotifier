package com.example.toolnotifier.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.toolnotifier.businessLogic.checkForNewTools
import com.example.toolnotifier.util.Log

class UpdateDateWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val didUpdate = checkForNewTools()

        return if (didUpdate == null) Result.failure() else Result.success()
    }
}