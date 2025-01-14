package com.example.tdm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tdm.data.model.OrderStatus
import com.example.tdm.data.model.Restaurant
import com.example.tdm.data.repository.*
import com.example.tdm.ui.screens.cart.CartScreen
import com.example.tdm.ui.screens.cart.CartViewModel
import com.example.tdm.ui.screens.cart.CartViewModelFactory
import com.example.tdm.ui.screens.home.HomeScreen
import com.example.tdm.ui.screens.login.LoginScreen
import com.example.tdm.ui.screens.orders.OrdersScreen
import com.example.tdm.ui.screens.signup.SignUpScreen
import com.example.tdm.ui.screens.restaurants.*
import com.example.tdm.ui.screens.tracking.OrderTrackingScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier, restaurantRepository: RestaurantRepository, authRepository: AuthRepository, orderRepository: OrderRepository) {
    val factory = CartViewModelFactory(
        orderRepository
    )

    val cartViewModel: CartViewModel = viewModel(factory = factory)


    NavHost(navController = navController, startDestination = "login", modifier = modifier) {

        composable("home") {
            HomeScreen(
                navController = navController,
                repository = restaurantRepository
            )
        }
        composable("order") {
            OrdersScreen(navController = navController, repository = orderRepository)
        }
        // Écran d'inscription
        composable("signup") {
            SignUpScreen(
                repository = authRepository,
                navController = navController,
                context = LocalContext.current
            )
        }

        composable("login") { LoginScreen(authRepository, navController, LocalContext.current) }
        composable("restaurantDetails") {
            val restaurant = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Restaurant>("restaurant") ?: return@composable

            RestaurantDetailsScreen(restaurant = restaurant, navController = navController)
        }
        composable("cart") {
            CartScreen(viewModel = cartViewModel, navController = navController)
        }
        composable(
            route = "orderTracking/{deliveryAddress}/{currentOrderStatus}",
            arguments = listOf(
                navArgument("deliveryAddress") { type = NavType.StringType },
                navArgument("currentOrderStatus") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val deliveryAddress = backStackEntry.arguments?.getString("deliveryAddress") ?: ""
            val currentOrderStatusString = backStackEntry.arguments?.getString("currentOrderStatus") ?: ""
            val currentOrderStatus = OrderStatus.valueOf(currentOrderStatusString)

            OrderTrackingScreen(
                deliveryAddress = deliveryAddress,
                currentOrderStatus = currentOrderStatus
            )
        }
        composable(
            route = "itemDetails/{itemId}/{itemName}/{itemPrice}/{restaurantName}/{restaurantId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType },
                navArgument("itemName") { type = NavType.StringType },
                navArgument("itemPrice") { type = NavType.FloatType },
                navArgument("restaurantName") { type = NavType.StringType },
                navArgument("restaurantId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            val itemName = backStackEntry.arguments?.getString("itemName") ?: ""
            val itemPrice = backStackEntry.arguments?.getFloat("itemPrice")?.toDouble() ?: 0.0
            val restaurantName = backStackEntry.arguments?.getString("restaurantName") ?: ""
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: -1

            ItemDetailsScreen(
                itemId = itemId,
                itemName = itemName,
                itemPrice = itemPrice,
                restaurantId = restaurantId,
                restaurantName = restaurantName,
                viewModel = cartViewModel, // Passe ton ViewModel partagé
                navController = navController
            )
        }

    }
}
