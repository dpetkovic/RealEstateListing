package com.dekisolutions.realestatelisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dekisolutions.realestatelisting.navigation.DetailScreenRoute
import com.dekisolutions.realestatelisting.navigation.ListingScreenRoute
import com.dekisolutions.realestatelisting.ui.screens.DetailScreen
import com.dekisolutions.realestatelisting.ui.screens.ListingScreen
import com.dekisolutions.realestatelisting.ui.theme.RealEstateListingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealEstateListingTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ListingScreenRoute,
                ) {
                    composable<ListingScreenRoute> {
                        ListingScreen(
                            onNavigateToDetail = { propertyId ->
                                navController.navigate(DetailScreenRoute(propertyId))
                            }
                        )
                    }
                    composable<DetailScreenRoute> { backStackEntry ->
                        DetailScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}