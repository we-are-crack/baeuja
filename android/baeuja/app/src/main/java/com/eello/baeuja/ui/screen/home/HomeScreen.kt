package com.eello.baeuja.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eello.baeuja.ui.component.ItemSeparator
import com.eello.baeuja.ui.component.RetryComponent
import com.eello.baeuja.ui.theme.BaujaTheme
import com.eello.baeuja.viewmodel.HomeLearningContent
import com.eello.baeuja.viewmodel.HomeViewModel
import com.eello.baeuja.viewmodel.NewContentItem
import timber.log.Timber

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    HomeRoute(homeViewModel)
}

@Composable
fun HomeRoute(homeViewModel: HomeViewModel) {
    val initLoadFailed by homeViewModel.initLoadFailed.collectAsState()
    val newContents = homeViewModel.newContents
    val homeLearningContents by homeViewModel.homeLearningContents.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        if (initLoadFailed) {
            homeViewModel.retryFetchInitHomeContents()
        }
    }

    if (initLoadFailed) {
        Timber.w("HomeContentSession 초기화 실패로 RetryComponent 출력")
        RetryComponent(onRetry = homeViewModel::retryFetchInitHomeContents)
    } else {
        HomeContent(
            newContents = newContents,
            homeLearningContents = homeLearningContents,
            isLoading = isLoading,
            onEndOfContents = homeViewModel::fetchMoreLearningContents
        )
    }
}

@Composable
fun HomeContent(
    newContents: List<NewContentItem> = emptyList(),
    homeLearningContents: List<HomeLearningContent> = emptyList(),
    isLoading: Boolean = false,
    onEndOfContents: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItemIndex >= totalItems
        }
    }

    LaunchedEffect(shouldLoadMore.value, isLoading) {
        if (shouldLoadMore.value && !isLoading) {
            onEndOfContents()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            if (newContents.isNotEmpty()) {
                item {
                    HomeNewContentItem(newContents)
                    ItemSeparator(4.dp)
                }
            }

            itemsIndexed(homeLearningContents) { index, content ->
                HomeLearningContentItem(content)
                if (index < homeLearningContents.size - 1) {
                    ItemSeparator(2.dp)
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    BaujaTheme {
        HomeContent()
    }
}