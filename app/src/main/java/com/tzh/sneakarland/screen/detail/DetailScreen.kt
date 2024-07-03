@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.tzh.sneakarland.screen.detail

import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.filament.IndirectLight
import com.tzh.sneakarland.R
import com.tzh.sneakarland.model.SneakerModel
import com.tzh.sneakarland.model.dummySneakerList
import com.tzh.sneakarland.screen.home.HomeScreen
import com.tzh.sneakarland.ui.component.SneakerImage
import com.tzh.sneakarland.util.AnimatedKeyExtension
import io.github.sceneview.Scene
import io.github.sceneview.animation.Transition.animateRotation
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun DetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    sneakerModel: SneakerModel,
    onDetailClick: () -> Unit
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailImage(
                !sharedTransitionScope.isTransitionActive,
                sneakerModel.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                animateModifier = Modifier.sharedElement(
                    state = rememberSharedContentState(
                        AnimatedKeyExtension.getImageKey(
                            sneakerModel.image.toString()
                        ),
                    ), animatedVisibilityScope = animatedContentScope
                ),
                onDetailClick = onDetailClick
            )

            //TitleText
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    sneakerModel.name,
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(
                            AnimatedKeyExtension.getTitleKey(
                                sneakerModel.name
                            )
                        ), animatedContentScope
                    )
                )
                Text(
                    sneakerModel.price,
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
                )
            }
            SizeSelectorContent(Modifier.fillMaxWidth())
            Text(text = "Description")
            Text(
                sneakerModel.description,
                style = MaterialTheme.typography.bodyMedium.copy(),
                modifier = Modifier
                    .alpha(0.5f)
                    .sharedElement(
                        state = rememberSharedContentState(
                            AnimatedKeyExtension.getDescKey(
                                sneakerModel.description
                            )
                        ), animatedContentScope
                    )
            )
        }

    }
}


@Composable
fun SizeSelectorContent(modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Size", style = MaterialTheme.typography.bodyLarge)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(8) {
                ElevatedCard(
                ) {
                    Text(
                        (it + 5).toString(),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun DetailImage(
    isTransactionDone: Boolean,
    image: Int,
    modifier: Modifier,
    animateModifier: Modifier,
    onDetailClick: () -> Unit
) {
    Box(modifier) {

        SneakerImage(
            image,
            Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .then(animateModifier)
        )
        if (isTransactionDone) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                onClick = onDetailClick,
                content = {
                    Icon(
                        painter = painterResource(R.drawable.detail_degrees),
                        contentDescription = image.toString()
                    )
                }
            )
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    SharedTransitionLayout() {
        AnimatedContent(targetState = true) {
            if (it) {
                DetailScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this,
                    sneakerModel = dummySneakerList.first()
                ) {}
            }
        }
    }
}