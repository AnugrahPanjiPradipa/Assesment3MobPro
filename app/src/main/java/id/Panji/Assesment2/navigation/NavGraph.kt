package id.Panji.Assesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.Panji.Assesment2.BuktiScreen
import id.Panji.Assesment2.MainScreen
import id.Panji.Assesment2.screen.DetailScreen
import id.Panji.Assesment2.screen.KEY_ID_NOMINAL

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }

        composable(route = Screen.Bukti.route) {
            BuktiScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_NOMINAL) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_NOMINAL)
            DetailScreen(navController, id)
            }
        }
}