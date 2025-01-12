package com.example.binlist.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.binlist.viewmodel.BinListVM
import com.example.binlist.R
import com.example.binlist.viewmodel.ShortBin

@Composable
fun BinListScreen(viewModel: BinListVM = hiltViewModel()) { //
    val backgroundSurfaceColor = Color(0xFFFFFFFF)
    val uiState by viewModel.uiState.collectAsState()
    val colors = listOf(
        Color(0xFF007CBE), // #007CBE
        Color(0xFFE57A44), // #E57A44
        Color(0xFF251351), // #251351
        Color(0xFFA882DD), // #A882DD
        Color(0xFFDB5375), // #DB5375
        Color(0xFFB3FFB3), // #B3FFB3
        Color(0xFF02C3BD), // #02C3BD
        Color(0xFF4E148C), // #4E148C
        Color(0xFF629460), // #629460
        Color(0xFFF4D35E), // #F4D35E
        Color(0xFF414288), // #414288
        Color(0xFFB0DB43), // #B0DB43
        Color(0xFFFFC145), // #FFC145
        Color(0xFFEC368D)  // #EC368D
    )

    Surface(
        color = backgroundSurfaceColor,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            uiState.content?.let { history ->
                itemsIndexed(history) {index: Int, item: ShortBin ->
                    ListCard(
                        index = index,
                        binID = item.id,
                        binValue = item.bin,
                        time = item.time,
                        bankName = item.bankName,
                        paymentSystem = item.paymentSystem,
                        countryName = item.countryName,
                        iconColor = colors.random()
                    )
                }
            }
        }
    }
}

@Composable
fun ListCard(
    index: Int,
    binID: Int,
    binValue: String,
    time: String,
    bankName: String?,
    paymentSystem: String?,
    countryName: String?,
    iconColor: Color
) {
    val textPadding = 2.dp
    val subTextColor = Color(0xFF4C5C68)
    val mainTextColor = Color(0xFF36454F)
    val containerColor = Color(0xFFEEEEEE)
    val fontFamily = FontFamily(Font(R.font.inter_18pt_bold))

    Card(
        onClick = {},
        colors = CardDefaults.cardColors(
            contentColor = Color.Unspecified,
            disabledContainerColor = containerColor,
            containerColor = containerColor,
            disabledContentColor = Color.Unspecified
        ),
        modifier = Modifier.padding(top = if(index!= 0) 15.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column(modifier = Modifier.weight(0.7f)) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 25.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.credit_card_24dp),
                    colorFilter = ColorFilter.tint(iconColor),
                    contentDescription = null
                )

                if (bankName != null) {
                    Text(
                        text = bankName,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = textPadding, bottom = 4.dp),
                        color = mainTextColor
                    )
                }
                Text(
                    text = binValue,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = textPadding, bottom = 4.dp),
                    color = subTextColor
                )
                if (paymentSystem != null) {
                    Text(
                        text = paymentSystem,
                        fontFamily = fontFamily,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = textPadding, bottom = 4.dp),
                        color = subTextColor
                    )
                }

                if (countryName != null) {
                    Text(
                        text = countryName,
                        fontFamily = fontFamily,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = textPadding),
                        color = subTextColor
                    )
                }
            }

            // Время
            Column(modifier = Modifier.weight(0.3f)) {
                Text(
                    text = time,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    color = subTextColor
                )
            }
        }
    }
}