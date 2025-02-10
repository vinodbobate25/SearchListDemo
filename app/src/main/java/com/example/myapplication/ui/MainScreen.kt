package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.ScrollableListTheme
import com.example.myapplication.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val images by viewModel.images.collectAsState()
    val filteredList by viewModel.filteredList.collectAsState()
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier =Modifier.fillMaxWidth().height(40.dp).background(Color.Cyan))

                    // Image Carousel
                    HorizontalPager(
                    count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 10.dp)
        ) { page ->
            ImageItem(imageRes = images[page])
        }
        SearchBar(
            onQueryChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.padding(16.dp))

        // List of Items
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredList) { item ->
                ListItem(label = item.label,item.id)
            }
        }
    }

    // Update list when pager page changes
    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateImagePosition(pagerState.currentPage)
    }
}

@Composable
fun SearchBar(onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            onQueryChange(it)
        },
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onQueryChange(query) }),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ImageItem(imageRes: Int) {
    // Use Coil or Glide for better image loading
    androidx.compose.foundation.Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ListItem(label: String,imageRes: Int) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    ScrollableListTheme {
        MainScreen(viewModel = MainViewModel())
    }
}