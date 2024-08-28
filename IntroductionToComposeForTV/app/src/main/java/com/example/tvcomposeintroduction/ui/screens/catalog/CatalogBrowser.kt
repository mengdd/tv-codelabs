/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tvcomposeintroduction.ui.screens.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.tvcomposeintroduction.data.Movie
import com.example.tvcomposeintroduction.ui.components.MovieCard

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CatalogBrowser(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = hiltViewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val categoryList by
    catalogBrowserViewModel.categoryList.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 48.dp, vertical = 32.dp)
    ) {
        item {
            val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsStateWithLifecycle()
            Carousel(
                itemCount = featuredMovieList.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(376.dp)
            ) { indexOfCarouselSlide ->
                val featuredMovie =
                    featuredMovieList[indexOfCarouselSlide]
                Box {
                    Text(text = featuredMovie.title)
                }
            }
        }
        items(categoryList) { category ->
            Text(text = category.name)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(category.movieList) { movie ->
                    MovieCard(movie = movie, onClick = { onMovieSelected(movie) })
                }
            }
        }
    }
}
