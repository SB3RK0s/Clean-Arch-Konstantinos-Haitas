package com.learningwithmanos.uniexercise.heroes.presenatation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.learningwithmanos.uniexercise.heroes.presenatation.ui.view.AppToolbar
import com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels.KeyEntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyEntryScreen(
    viewModel: KeyEntryViewModel = hiltViewModel(),
    navController: NavController
) {
    var publicKey by remember { mutableStateOf(viewModel.publicKey) }
    var privateKey by remember { mutableStateOf(viewModel.privateKey) }
    val isEnabled = publicKey.isNotEmpty() && privateKey.isNotEmpty()

    Scaffold(
        topBar = {
            AppToolbar(title = "Settings") {
                navController.navigate("heroesScreen")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = publicKey,
                onValueChange = { publicKey = it },
                label = { Text("Public Key") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = privateKey,
                onValueChange = { privateKey = it },
                label = { Text("Private Key") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.onSaveButtonClicked(publicKey, privateKey)
                    navController.popBackStack() // Navigates back to the previous screen
                },
                enabled = isEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Keys")
            }

        }
    }
}

