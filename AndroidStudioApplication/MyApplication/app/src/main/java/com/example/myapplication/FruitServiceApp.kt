package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.screens.AddFruitScreen
import com.example.myapplication.screens.CalculateCostScreen
import com.example.myapplication.screens.DeleteFruitScreen
import com.example.myapplication.screens.GenerateReceiptScreen
import com.example.myapplication.screens.UpdateFruitScreen
import com.example.myapplication.viewmodel.FruitViewModel

//class FruitServiceApp(viewModel: FruitViewModel) {
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitServiceApp(
    viewModel: FruitViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs =
        listOf("Add Fruit", "Update Fruit", "Delete Fruit", "Calculate Cost", "Generate Receipt")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Fruit Service Engine",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            viewModel.clearMessages()
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            }

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedTab) {
                    0 -> AddFruitScreen(viewModel)
                    1 -> UpdateFruitScreen(viewModel)
                    2 -> DeleteFruitScreen(viewModel)
                    3 -> CalculateCostScreen(viewModel)
                    4 -> GenerateReceiptScreen(viewModel)
                }
            }
        }
    }
}
