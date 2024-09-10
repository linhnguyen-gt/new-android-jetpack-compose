package com.example.newandroidjetpackcompose.views.page_two

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newandroidjetpackcompose.apis.ResponseData
import com.example.newandroidjetpackcompose.models.ResponseDataModel
import com.example.newandroidjetpackcompose.view_model_factory.ResponseViewModelFactory
import com.example.newandroidjetpackcompose.view_models.ResponseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageTwoScreen() {
    val responseData = ResponseData()
    val viewModel: ResponseViewModel = viewModel(
        factory = ResponseViewModelFactory(responseData)
    )

    val data by viewModel.data.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Page One") }
            )
        }
    ) { paddingValues ->
        when {
            error != null -> {
                ErrorMessage(error!!)
            }

            data?.data != null -> {
                StateList(data?.data!!, paddingValues)
            }

            else -> {
                LoadingIndicator()
            }
        }
    }
}

@Composable
fun StateList(states: List<ResponseDataModel>, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(states) { state ->
            StateItem(state)
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message", color = Color.Black)
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun StateItem(state: ResponseDataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = state.state ?: "Unknown State")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Population: ${state.population ?: "N/A"}")
            Text(text = "Year: ${state.year ?: "N/A"}")
        }
    }
}