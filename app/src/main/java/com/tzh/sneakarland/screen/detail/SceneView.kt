package com.tzh.sneakarland.screen.detail

import android.graphics.drawable.ColorDrawable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.filament.Renderer
import com.google.ar.sceneform.rendering.ViewRenderable
import io.github.sceneview.Scene
import io.github.sceneview.animation.Transition.animateRotation
import io.github.sceneview.collision.Vector3
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.toFloat3
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberNodes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun SceneView(
    tModel: String,
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    val cameraNode = rememberCameraNode(engine).apply {
        position = Position(z = 2.0f)
    }
    val centerNode = rememberNode(engine).addChildNode(cameraNode)
    val cameraTransition = rememberInfiniteTransition(label = "CameraTransition")
    val cameraRotation by cameraTransition.animateRotation(
        initialValue = Rotation(y = 0.0f),
        targetValue = Rotation(y = 360.0f),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7.seconds.toInt(DurationUnit.MILLISECONDS))
        )
    )
    val childNodes = rememberNodes { }
    LaunchedEffect(Unit) {
        childNodes.addAll(
            listOf(
                centerNode, ModelNode(
                    modelInstance = modelLoader.createModelInstance(
                        assetFileLocation = tModel
                    ), scaleToUnits = 1.0f
                )
            )
        )
    }

    Scene(modifier = Modifier.fillMaxSize(),
        engine = engine,
        modelLoader = modelLoader,
        cameraNode = cameraNode,
        childNodes = childNodes,
        environmentLoader = environmentLoader,
        onFrame = {
            centerNode.rotation = cameraRotation
            cameraNode.lookAt(centerNode)
        },
        onViewCreated = {

            // Set the background color to white

            val argb = Color.White.toArgb()
            val alpha = android.graphics.Color.alpha(argb)
            val red = android.graphics.Color.red(argb)
            val green = android.graphics.Color.green(argb)
            val blue = android.graphics.Color.blue(argb)
            val sceneColor = com.google.ar.sceneform.rendering.Color(
                red / 256F, green / 256F, blue / 256F, alpha / 256F
            )
            renderer.clearOptions.clear = false

            renderer.clearOptions.clearColor =
                floatArrayOf(red / 256F, green / 256F, blue / 256F, alpha / 256F)


//            ViewRenderable.builder().setView(context, this).build(engine).thenAccept { view ->
//                    val bgNode = Node(engine).apply {
//                        scale = Vector3(4f, 4f, 4f).toFloat3()
//                        name = "bg"
//                        isVisible = true
//                        position = Vector3(0f, -2f, 0f).toFloat3()
//                    }
//                    childNodes.add(bgNode)
//                }
        })
}
