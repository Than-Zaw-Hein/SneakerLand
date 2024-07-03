package com.tzh.sneakarland

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tzh.sneakarland.model.SneakerModel
import com.tzh.sneakarland.navigation.ArViewRoute
import com.tzh.sneakarland.navigation.DetailRoute
import com.tzh.sneakarland.navigation.HomeRoute
import com.tzh.sneakarland.screen.detail.DetailScreen
import com.tzh.sneakarland.screen.detail.SceneView
import com.tzh.sneakarland.screen.home.HomeScreen
import com.tzh.sneakarland.ui.theme.SneakarLandTheme

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SneakarLandTheme {

//                val onDetailClickListener: (() -> Unit)? by remember { mutableStateOf(null) }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SharedTransitionLayout(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = HomeRoute) {
                            composable<HomeRoute> {
                                HomeScreen(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this
                                ) {
                                    navController.navigate(
                                        DetailRoute(
                                            image = it.image,
                                            name = it.name,
                                            description = it.description,
                                            price = it.price,
                                            tModel = it.tModel
                                        )
                                    )
                                }
                            }

                            composable<DetailRoute> {
                                val args = it.toRoute<DetailRoute>()
                                DetailScreen(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this,
                                    SneakerModel(
                                        image = args.image,
                                        name = args.name,
                                        description = args.description,
                                        price = args.price,
                                        tModel = args.tModel
                                    )
                                ) {
                                    navController.navigate(ArViewRoute(args.tModel))
                                }
                            }

                            composable<ArViewRoute> {
                                val args = it.toRoute<ArViewRoute>()
                                SceneView(args.tModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

