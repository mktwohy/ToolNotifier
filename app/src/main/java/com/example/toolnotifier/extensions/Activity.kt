package com.example.toolnotifier.extensions

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ComponentActivity.viewModel(): T =
    ViewModelProvider(this).get(T::class.java)