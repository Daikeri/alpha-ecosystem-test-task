package com.example.bincheck.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
    var titleAlpha by remember {
        mutableStateOf(1f)
    }
    val animatedTitleAlpha = remember {
        Animatable(titleAlpha)
    }
    val paddingCardSurface by remember {
        mutableStateOf(0f)
    }
    val animatedPaddingCardSurface = remember {
        Animatable(paddingCardSurface)
    }
    LaunchedEffect(key1 = shoBinInfo) {
        if (shoBinInfo) {
            animatedTitleAlpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = CubicBezierEasing(0.2f, 0f, 0f, 1f))
            )
            animatedPaddingCardSurface
                .animateTo(
                    targetValue = 600f,
                    animationSpec = tween(durationMillis = 850, easing = CubicBezierEasing(0.2f, 0f, 0f, 1f))
                )
        }
    }

    val onInputValueChange = { inputValue: String -> inputFieldState = inputValue}
    val backgroundSurfaceColor = Color(0xFFFFFFFF)
    val titleColor = Color(0xFF36454F)
    val cardSurfaceColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFFDCDCDD),
            Color(0xFFC5C3C6),
            Color(0xFF899097),
            Color(0xFF4C5C68),
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundSurfaceColor
    ) {
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(bottom = animatedPaddingCardSurface.value.dp)

       ) {
           Title(
               alphaState = animatedTitleAlpha,
               color = titleColor,
               modifier = Modifier.align(Alignment.TopStart)
           )

           CardSurface(
               offsetState = animatedPaddingCardSurface,
               brush = cardSurfaceColor,
               inputFieldState = inputFieldState,
               onDone = { shoBinInfo = !shoBinInfo},
               onInputValueChange = onInputValueChange,
               modifier = Modifier
                   .align(Alignment.Center)
           )
       }
    }
}


@Composable
fun Title(
    alphaState: Animatable<Float, AnimationVector1D>,
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
                alpha = alphaState.value
            }
    )
}

@Composable
fun CardSurface(
    offsetState: Animatable<Float, AnimationVector1D>,
    brush: Brush,
    inputFieldState: String,
    onInputValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var downloadIndicatorIsVisible by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .height(230.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = brush)
            //.padding(offsetState.value.dp)
            //.offset(y = offsetState.value.dp)
            .then(modifier)
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