package com.sarang.torang.compose.feedgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SearchView(onSearch : () -> Unit = {}){
    Row(Modifier.fillMaxWidth()
        .padding(start = 12.dp, end = 12.dp, bottom = 16.dp, top = 10.dp)
        .clickable { onSearch() },
        verticalAlignment = Alignment.CenterVertically
    ){
        SearchViewText()
    }
}

@Preview
@Composable
private fun SearchViewText(){
    Row(modifier = Modifier.clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFEEEEEE))
                            .height(40.dp)
                            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(12.dp))
        SearchIcon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "검색", color = Color.DarkGray)
    }
}

@Preview
@Composable
fun SearchBar(modifier : Modifier = Modifier){
    var text by remember { mutableStateOf("") }
    BasicTextField(
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        value = text,
        onValueChange = {
            text = it
        },
        textStyle = LocalTextStyle.current.copy(
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
    ){
        Row(modifier = Modifier.background(Color(0xFFEEEEEE))
                               .height(40.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(12.dp))
            SearchIcon()
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                it.invoke()
                if(text.isEmpty())
                    Text(text = "검색", color = Color.DarkGray)
            }
        }
    }
}

@Preview
@Composable
fun SearchIcon(){
    Icon(imageVector = Icons.Default.Search,
        contentDescription = null,
        tint = Color.DarkGray)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextSearch(onSearchBack : () -> Unit = {}){
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onSearchBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        // ... 기타 설정
                    )
                }
            })
    }) {
        Box(Modifier.padding(it))
    }
}