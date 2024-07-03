package com.tzh.sneakarland.screen.home


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzh.sneakarland.model.SneakerModel
import com.tzh.sneakarland.model.dummySneakerList
import com.tzh.sneakarland.ui.component.SneakerImage
import com.tzh.sneakarland.util.AnimatedKeyExtension

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onSneakerClick: (SneakerModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        Categories()
        Spacer(modifier = Modifier.height(16.dp))
        SneakerList(
            modifier = Modifier.weight(1f),
            sharedTransitionScope,
            animatedContentScope,
            onSneakerClick
        )
    }
}

@Composable
fun SearchBar() {
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        },
        placeholder = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White, focusedContainerColor = Color.White
        ),
        shape = CircleShape
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Categories() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dummySneakerList) {
            ElevatedCard(
                onClick = {},
                shape = CircleShape,
            ) {
                Text(text = it.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SneakerList(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onSneakerClick: (SneakerModel) -> Unit
) {
    with(sharedTransitionScope) {
        LazyColumn(modifier = modifier) {
            items(dummySneakerList) {
                SneakerItem(
                    it,
                    imageModifier = Modifier.sharedElement(
                        state = rememberSharedContentState(AnimatedKeyExtension.getImageKey(it.image.toString())),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    titleModifier = Modifier.sharedElement(
                        state = rememberSharedContentState(AnimatedKeyExtension.getTitleKey(it.name)),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    descModifier = Modifier.sharedElement(
                        state = rememberSharedContentState(AnimatedKeyExtension.getDescKey(it.description)),
                        animatedVisibilityScope = animatedContentScope
                    )
                ) {
                    onSneakerClick(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SneakerItem(
    sneakerModel: SneakerModel,
    imageModifier: Modifier,
    titleModifier: Modifier,
    descModifier: Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            hoveredElevation = 4.dp,
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SneakerImage(
                sneakerModel.image, imageModifier
                    .size(64.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = sneakerModel.name, fontSize = 18.sp, modifier = titleModifier)
                Text(
                    text = sneakerModel.description,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = descModifier
                )
                Text(
                    text = sneakerModel.price,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
            }
            IconButton(
                onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Favorite")
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SharedTransitionLayout() {
        AnimatedContent(targetState = true) {
            if (it) {
                HomeScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this
                ) {}
            }
        }
    }
}