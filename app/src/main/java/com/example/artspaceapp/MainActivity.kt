package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}


data class ArtDataDTO(
    var artistName: Int,
    var artDescription: Int,
    var artPhoto: Int
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentImageStep by remember { mutableStateOf(0) }
    val artList = listOf(
        ArtDataDTO(R.string.salvador_dali, R.string.desierta_desc, R.drawable.desertia),
        ArtDataDTO(R.string.salvador_dali, R.string.galatea_desc, R.drawable.galatea_of_the_spheres),
        ArtDataDTO(R.string.hieronymus_bosch, R.string.the_creation_of_the_world_desc, R.drawable.the_creation_of_the_world),
        ArtDataDTO(R.string.hieronymus_bosch, R.string.ascent_of_the_blessed_desc, R.drawable.paradise_ascent_of_the_blessed),
    )
    fun showPreviousImage() {
        if (currentImageStep <= 0) currentImageStep = artList.size - 1 else currentImageStep--
    }
    fun showNextImage() {
        if (currentImageStep >= artList.size - 1) currentImageStep = 0 else currentImageStep++
    }
    Column (
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        ArtWorkWall(
            artData = artList[currentImageStep],
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(64.dp))
        DisplayController(
            onPreviousClick = { showPreviousImage() },
            onNextClick = { showNextImage() },
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun DisplayController(onPreviousClick: () -> Unit, onNextClick: () -> Unit, modifier: Modifier = Modifier) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onPreviousClick() },
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 5))
                .size(width = 120.dp, height = 42.dp)
        ) {
            Text(
                text = stringResource(id = R.string.previous_button),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = { onNextClick() },
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 5))
                .size(width = 120.dp, height = 42.dp)
        ) {
            Text(
                text = stringResource(id = R.string.next_button),
                fontSize = 16.sp
            )
        }
    }
}
        
@Composable
fun ArtWorkWall(artData: ArtDataDTO, modifier: Modifier = Modifier) {
    var showDescription by remember { mutableStateOf(true) }
    Column (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 32.dp,
                    shape = RoundedCornerShape(8.dp)
                )
            ,
            contentAlignment = Alignment.BottomStart,
        ) {
            Image(
                painter = painterResource(id = artData.artPhoto),
                contentDescription = artData.artDescription.toString(),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(540.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(percent = 5))
                    .clickable { showDescription = !showDescription }
            )
            ArtWorkDescription(
                artName =  artData.artDescription,
                artistName = artData.artistName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(bottomStartPercent = 5, bottomEndPercent = 5))
                    .alpha(if (showDescription) 1f else 0f)
            )
        }
    }
}

@Composable
fun ArtWorkDescription(artName: Int, artistName: Int, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.3f)
            )
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Text(
            text = stringResource(id = artName),
            color = Color.White,
            fontSize = 32.sp,
            lineHeight = 32.sp
        )
        Spacer(
            modifier = Modifier.height(6.dp)
        )
        Text(
            text = stringResource(id = artistName),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.W600
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
    }
}