package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlin.reflect.KMutableProperty0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtworkScreen()
                }
            }
        }
    }
}

@Composable
fun ArtworkCard(
    modifier: Modifier = Modifier,
    @DrawableRes imgRes: Int,
    onClick: () -> Unit
) {
    Card (
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = modifier
            .size(width = 360.dp, height = 480.dp)
    ) {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imgRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(15.dp)
                    .clickable(onClick = onClick),
                alignment = Alignment.Center
            )
        }
    }
}

@Composable
fun ArtworkInfo(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes authorRes: Int,
    @StringRes yearRes: Int
) {
    val author = stringResource(authorRes)
    val year = "(" +  stringResource(yearRes) + ")"
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffd9ffff),
        ),
        modifier = modifier
            .width(360.dp)
            .height(100.dp)
    ) {
        Text (
            text = stringResource(titleRes),
            fontSize = 24.sp,
            modifier = modifier
                .padding(
                    start = 15.dp,
                    top = 6.dp
                )
        )
        Text (
            buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append( author )
                    }
                    append("  $year")
                }
            },
            modifier = modifier.padding(
                start = 15.dp,
                top = 6.dp,
                bottom = 15.dp
            )
        )
    }
}

@Composable
fun ArtworkDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int
) {
    val description = stringResource(descriptionRes)
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffd9ffff),
        ),
        modifier = modifier
            .width(360.dp)
            .height(100.dp)
    ) {
        Box (
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = description,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontStyle = FontStyle.Italic),
                textAlign = TextAlign.Justify,
                modifier = modifier.padding(
                    start = 15.dp,
                    end = 15.dp
                )
            )
        }
    }
}

@Composable
fun PressButton(
    modifier: Modifier = Modifier,
    onNextPressed: () -> Unit,
    onPreviousPressed: () -> Unit
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .padding(start = 45.dp)
                .weight(1f),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = onPreviousPressed,
                modifier = Modifier.size(width = 128.dp, height = 36.dp)
            ) {
                Text(
                    text = "Previous",
                    fontSize = 16.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(end = 45.dp)
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                onClick = onNextPressed,
                modifier = Modifier.size(width = 128.dp, height = 36.dp)
            ) {
                Text(
                    text = "Next",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun ArtworkScreen(modifier: Modifier = Modifier) {
    val artAmount = 10

    var showInfo by remember { mutableStateOf(true) }

    var artId by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val imgRes = context.resources.getIdentifier("img$artId", "drawable", context.packageName)
    val titleRes = context.resources.getIdentifier("title$artId", "string", context.packageName)
    val authorRes = context.resources.getIdentifier("author$artId", "string", context.packageName)
    val yearRes = context.resources.getIdentifier("year$artId", "string", context.packageName)
    val descriptionRes = context.resources.getIdentifier("description$artId", "string", context.packageName)
    Box (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff627b91))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(96.dp))
            ArtworkCard(imgRes = imgRes, onClick = { showInfo = !showInfo })
            Spacer(modifier = modifier.height(64.dp))
            if (showInfo) {
                ArtworkInfo(titleRes = titleRes, authorRes = authorRes, yearRes = yearRes)
            } else {
                ArtworkDescription(descriptionRes = descriptionRes)
            }
            Spacer(modifier = Modifier.weight(1f))
            PressButton(
                onPreviousPressed = {
                    artId = when(artId) {
                        0 -> artAmount - 1
                        else -> artId - 1
                    }
                    showInfo = true
                },
                onNextPressed = {
                    artId = (artId + 1) % artAmount
                    showInfo = true
                }
            )
        }
    }
}

@Preview
@Composable
fun ArtworkPreview() {
    ArtSpaceTheme {
        ArtworkScreen()
    }
}
