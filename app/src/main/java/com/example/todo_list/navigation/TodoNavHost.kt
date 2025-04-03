package com.example.todo_list.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todo_list.ui.feature.AddEditScrean
import com.example.todo_list.ui.feature.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object  ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
            // antigamente se passava uma rota com um todolist, que retornaria uma string
            // sempre dava erro ma execuçao
    NavHost(navController = navController , startDestination = ListRoute ){
            composable<ListRoute> {
                ListScreen(
                    navigationToAddEditScrean = {id ->
                        navController.navigate(AddEditRoute(id = id))
                    }
                )
            }
        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScrean()
        }
    }
}