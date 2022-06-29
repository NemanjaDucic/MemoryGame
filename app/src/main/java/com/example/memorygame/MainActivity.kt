package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.ui.CardsModel
import com.example.memorygame.ui.MemmoryGameViewModel
import com.example.memorygame.ui.theme.PraksaComposeTheme

class MainActivity : ComponentActivity() {
    val viewModel: MemmoryGameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.randomizeCards()
        setContent {
            PraksaComposeTheme {
                MainContent()
            }
        }
    }


    @Composable
    private fun MainContent() {
        Scaffold(
            topBar={
                TopAppBar(
                    title={
                        Text(text=stringResource(id=R.string.app_name))
                    },
                    actions={
                        IconButton(onClick={ viewModel.randomizeCards() }) {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription="Reload Game"
                            )
                        }
                    }
                )
            }
        ) {
            val cards: List<CardsModel> by viewModel.getCards().observeAsState(listOf())
            CardsGrid(cards=cards)
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CardsGrid(cards: List<CardsModel>) {
        LazyVerticalGrid(
            cells=GridCells.Fixed(4)
        ) {
            items(cards.count()) { cardIndex ->
                CardItem(cards[cardIndex])
            }
        }
    }

    @Composable
    private fun CardItem(card: CardsModel) {
        Box(
            modifier=Modifier
                .padding(all=10.dp)
        ) {
            Column(
                horizontalAlignment=Alignment.CenterHorizontally,
                verticalArrangement=Arrangement.Center,
                modifier=Modifier
                    .size(150.dp)
                    .background(
                        color=Color.Black.copy(alpha=if (card.isVisible) 0.4F else 0.0F),
                        shape=RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        if (card.isVisible) {
                            viewModel.updateShowVisibleCard(card.id)
                        }
                    }

            ) {
                if (card.isSelect) {
                    Text(
                        text=card.char,
                        fontSize=32.sp
                    )
                }
            }
        }
    }
}