package com.learningwithmanos.uniexercise.heroes.presenatation.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.presenatation.ui.view.AppToolbar
import com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels.HeroTileModel
import com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels.HeroesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(
    viewModel: HeroesViewModel = hiltViewModel(),
    navController: NavController
) {

    val selectedTab = viewModel.selectedTabStateFlow.collectAsState()
    val heroesUiState = viewModel.heroesStateFlow.collectAsState()

    Scaffold(
        topBar = {
            AppToolbar(title = "Heroes List") {
                navController.navigate("keyEntryScreen")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = viewModel.getSelectedIndex(selectedTab.value)) {
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.Heroes) },
                    textAlign = TextAlign.Center,
                    text = "Heroes"
                )
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByNameHeroes) },
                    textAlign = TextAlign.Center,
                    text = "A-Z Heroes"
                )
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByComicHeroes) },
                    textAlign = TextAlign.Center,
                    text = "Heroes by Comic"
                )
            }
            heroesUiState.value.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            } ?: run {
                // If no error message, display the heroes list
                ShowHeroes(heroes = heroesUiState.value.heroes)
            }
        }
    }
}

@Composable
fun ShowHeroes(heroes: List<HeroTileModel>) {
    LazyColumn {
        items(heroes) { hero ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Left half for the Image
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                ) {
                    Log.d("ImageLoading", "Loading image at URL: ${hero.imageUrl}")
                    AsyncImage(
                        model = hero.imageUrl,
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                // Right half for the Text
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(text = hero.title)
                }
            }
        }
    }
}
