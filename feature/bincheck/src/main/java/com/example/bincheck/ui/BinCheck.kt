package com.example.bincheck.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bincheck.viewmodel.BinCheckVM
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bincheck.R

@Composable
fun BinCheckScreen(viewModel: BinCheckVM = hiltViewModel()) {
    val uiState = viewModel.uiState.observeAsState()
    var inputFieldState by remember {
        mutableStateOf("")
    }
    var shoBinInfo by remember {
        mutableStateOf(false)
    }

    val transition = updateTransition(targetState = shoBinInfo, label = "global animation")

    val startColorGradient1 = Color(0xFFDCDCDD)
    val startColorGradient2 = Color(0xFFC5C3C6)
    val startColorGradient3 = Color(0xFF899097)
    val startColorGradient4 =  Color(0xFF4C5C68)

    val altColorGradient11 = Color(0xFF737DFE)
    val altColorGradient22 = Color(0xFFFFCAC9)
    /*
        val altColorGradient11 = Color(0xFFC6FFDD)
    val altColorGradient22 = Color(0xFFFBD7B6)
    val altColorGradient33 = Color(0xFFF7797D)
     */

    /*
     val altColorGradient11 = Color(0xFF233A4E)
    val altColorGradient22 = Color(0xFF9C6DB0)
     */

    /*
    val altColorGradient11 = Color(0xFF737DFE)
    val altColorGradient22 = Color(0xFFFFCAC9)
     */

    val customEasing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
    val gradientDuration = 2000
    val gradientDelay = 300

    val stateColorGradient1 by transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = gradientDelay,durationMillis = gradientDuration, easing = customEasing) }
    ) { state ->
        if (!state) startColorGradient1 else altColorGradient11
    }
    val stateColorGradient2 by transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = gradientDelay,durationMillis = gradientDuration, easing = customEasing) }
    ) { state ->
        if (!state) startColorGradient2 else altColorGradient11
    }
    val stateColorGradient3 by transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = gradientDelay,durationMillis = gradientDuration, easing = customEasing) }
    ) { state ->
        if (!state) startColorGradient3 else altColorGradient22
    }
    val stateColorGradient4 by transition.animateColor(
        label = "",
        transitionSpec = { tween(delayMillis = gradientDelay,durationMillis = gradientDuration, easing = customEasing) }
    ) { state ->
        if (!state) startColorGradient4 else altColorGradient22
    }
    val elevationState by transition.animateDp(
        label = "",
        transitionSpec = { tween(delayMillis = 1600,durationMillis = 800, easing = customEasing) }
    ) { state ->
        if (!state) 0.dp else 30.dp
    }
    val offsetCardSurfaceState by transition.animateFloat(
        label = "",
        transitionSpec = { tween(delayMillis = 0,durationMillis = 800, easing = customEasing) }
    ) { state ->
        if (!state) 0f else 300f
    }
    val alphaTitleState by transition.animateFloat(
        label = "",
        transitionSpec = {tween(delayMillis = 0,durationMillis = 600, easing = customEasing) }
    ) { state ->
        if (!state) 1f else 0f
    }
    val compositionGradient by remember {
        derivedStateOf {
            if (shoBinInfo) {
                Brush.linearGradient(
                    colors = listOf(
                        stateColorGradient1,
                        stateColorGradient4
                    )
                )
            } else {
                Brush.linearGradient(
                    colors = listOf(
                        stateColorGradient1,
                        stateColorGradient2,
                        stateColorGradient3,
                        stateColorGradient4
                    )
                )
            }
        }
    }


    val onInputValueChange = { inputValue: String -> inputFieldState = inputValue}
    val backgroundSurfaceColor = Color(0xFFFFFFFF)
    val titleColor = Color(0xFF36454F)

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundSurfaceColor
    ) {
       Box(
           modifier = Modifier
               .fillMaxSize()
       ) {
           Title(
               alphaState = alphaTitleState,
               color = titleColor,
               modifier = Modifier.align(Alignment.TopStart)
           )

           CardSurface(
               brush = compositionGradient,
               elevationState = elevationState,
               inputFieldState = inputFieldState,
               offsetState = offsetCardSurfaceState,
               onDone = { shoBinInfo = !shoBinInfo},
               onInputValueChange = onInputValueChange,
               modifier = Modifier
                   .align(Alignment.Center)
           )

           transition.AnimatedVisibility(
               modifier = Modifier
                   .align(Alignment.BottomCenter)
               ,
               visible = { it },
               enter = slideInVertically(
                   animationSpec = tween(
                       durationMillis = 500,
                       delayMillis = 1050,
                       easing = customEasing
                   ),
                   initialOffsetY = { fullHeight -> fullHeight }
               ) + fadeIn(
                   animationSpec = tween(
                       durationMillis = 500,
                       easing = customEasing
                   ),
               ), // Плавное появление
               exit = slideOutVertically(
                   animationSpec = tween(
                       durationMillis = 500,
                       easing = customEasing
                   ),
                   targetOffsetY = { fullHeight -> fullHeight }
               ) + fadeOut(
                   animationSpec = tween(
                       durationMillis = 500,
                       easing = customEasing
                   ),
               ) // Плавное исчезновение
           ) {
               InfoDetailSurface()
           }
       }
    }
}


@Composable
fun Title(
    alphaState: Float,
    color: Color,
    modifier: Modifier
) {
    Text(
        text =
        buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = color,
                    fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
                    fontSize = 68.sp,
                )
            ) {
                append("Enter your")
            }
            append("\n")
            withStyle(
                SpanStyle(
                    color = color,
                    fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
                    fontSize = 78.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("BIN.")
            }
        },
        fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
        fontSize = 68.sp,
        color = Color(0xFF36454F),
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(top = 97.dp, start = 20.dp, end = 20.dp)
            .then(modifier)
            .graphicsLayer {
                alpha = alphaState
            }
    )
}

@Composable
fun CardSurface(
    brush: Brush,
    elevationState: Dp,
    inputFieldState: String,
    offsetState: Float,
    onInputValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var downloadIndicatorIsVisible by remember {
        mutableStateOf(false)
    }
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = elevationState,
        modifier = Modifier
            .then(modifier)
            .height(230.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp,)
            .offset(y = -(offsetState).dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        ) {
            Image(
                imageVector =
                ImageVector.vectorResource(id = R.drawable.google_chio_silver),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(95.dp)
                    .padding(start = 15.dp, bottom = 25.dp)
                    .align(Alignment.CenterStart)
            )

            InputFieldCardSurface(
                state = inputFieldState,
                onValueChange = onInputValueChange,
                onDone = {
                    downloadIndicatorIsVisible = !downloadIndicatorIsVisible
                    onDone()
                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 55.dp)
                    .align(Alignment.CenterStart)
            )

            if (downloadIndicatorIsVisible) {
                LinearProgressIndicator(
                    color = Color(0xFF7096D1),
                    trackColor = Color(0xFFBAD6EB),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    }

}

@Composable
fun InputFieldCardSurface(
    state: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localFocus = LocalFocusManager.current

    Row(
        modifier = modifier
            .then(modifier)
    ) {
        OutlinedTextField(
            value = state,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            textStyle = TextStyle(
                color =  Color(0xFF4C5C68),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ocrabeckerrus_lat))
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocus.clearFocus()
                    onDone()
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(0.0f),
                unfocusedContainerColor = Color.White.copy(0.0f),
                focusedIndicatorColor = Color(0xFF36454F),
                unfocusedIndicatorColor = Color(0xFF899097),
                cursorColor = Color(0xFF4C5C68)
            ),
            modifier = Modifier
                .width(110.dp)//110
                .height(56.dp),
        )

        Text(
            text = "XX",
            fontSize = 18.sp,
            color = Color(0xFFDCDCDD),
            fontFamily = FontFamily(Font(R.font.ocrabeckerrus_lat)),
            style = TextStyle(
                baselineShift = BaselineShift(-1.3f),
                textIndent = TextIndent(firstLine = 10.sp)
            )
        )
        Text(
            text = "XXXX",
            fontSize = 18.sp,
            color = Color(0xFFDCDCDD),
            fontFamily = FontFamily(Font(R.font.ocrabeckerrus_lat)),
            style = TextStyle(
                baselineShift = BaselineShift(-1.3f),
                textIndent = TextIndent(firstLine = 30.sp)
            )
        )
        Text(
            text = "XXXX",
            fontSize = 18.sp,
            color = Color(0xFFDCDCDD),
            fontFamily = FontFamily(Font(R.font.ocrabeckerrus_lat)),
            style = TextStyle(
                baselineShift = BaselineShift(-1.3f),
                textIndent = TextIndent(firstLine = 30.sp)
            )
        )
    }
}

@Composable
fun InfoDetailSurface(
    modifier: Modifier = Modifier
) {
    val fontFamily = FontFamily(Font(R.font.inter_18pt_regular))
    val headFontSize = 21.sp
    val contentFontSize = 19.sp
    val headColor = Color(0xFF36454F)
    val contentColor = Color(0xFF4C5C68)


    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEEEEEE),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 275.dp)
            .then(modifier)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,//Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally) ,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
        ) {
            val contentForBinaryType = listOf(
                buildAnnotatedString {
                    append("Debit / ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Credit")
                    }
                },
                buildAnnotatedString {
                    append("Yes / ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append("No")
                    }
                },
            )
            val headMono = listOf(
                "Scheme / network",
                "Brand",
                "Country",
                "Bank"
            )
            val headBinary = listOf(
                "Type",
                "Prepaid",
            )
            Column {
                headMono.forEachIndexed { index, s ->
                    Column(
                        modifier.padding(
                            bottom = if (index != headMono.size-1 ) 15.dp else 0.dp
                        )
                    ) {
                        Text(
                            text = s,
                            fontSize = headFontSize,
                            color = headColor,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(
                                bottom = 10.dp,
                            )
                        )
                        Text(
                            text = "Some content",
                            fontSize = contentFontSize,
                            color = contentColor,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
            Column {
                headBinary.forEachIndexed { index, s ->
                    Column(
                        modifier.padding(
                            bottom = if (index != headBinary.size-1 ) 15.dp else 0.dp
                        )
                    ) {
                        Text(
                            text = s,
                            fontSize = headFontSize,
                            color = headColor,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(
                                bottom = 10.dp,
                            )
                        )
                        Text(
                            text = contentForBinaryType[index],
                            fontSize = contentFontSize,
                            color = contentColor,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}


/*
Удачная рандом палетка
Color(0xFF373B4D), // Темный серо-синий
Color(0xFF949597), // Серый
Color(0xFFBDBFB7), // Оливково-серый
*/


/*
11. Slate Gray Color Palette
Color(0xFF576575),
Color(0xFF708090),
Color(0xFF9AA4BA),
Color(0xFFAFB6CF),
Color(0xFFC3C8E3),
 */

/*
27. Ash Gray Color Palette
Color(0xFFE8EEEA),
Color(0xFFCDD6D0),
Color(0xFFB2BEB5),
Color(0xFF6D756F),
Color(0xFF282C29),
 */

/*
Итоговая палитра
20. Charcoal Gray Color Palette
#36454F, #4C5C68, #899097, #C5C3C6, #DCDCDD
Color(0xFF36454F),
Color(0xFF4C5C68),
Color(0xFF899097),
Color(0xFFC5C3C6),
Color(0xFFDCDCDD),
 */

/*
val cardSurfaceColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFFDCDCDD),
            Color(0xFFC5C3C6),
            Color(0xFF899097),
            Color(0xFF4C5C68),
        )
    )
 */

