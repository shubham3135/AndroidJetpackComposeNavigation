package com.shubhamkumarwinner.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shubhamkumarwinner.composenavigation.RallyScreen.*
import com.shubhamkumarwinner.composenavigation.data.Bill
import com.shubhamkumarwinner.composenavigation.data.UserData
import com.shubhamkumarwinner.composenavigation.ui.accounts.AccountsBody
import com.shubhamkumarwinner.composenavigation.ui.bills.BillsBody
import com.shubhamkumarwinner.composenavigation.ui.components.RallyTabRow
import com.shubhamkumarwinner.composenavigation.ui.overview.OverviewBody
import com.shubhamkumarwinner.composenavigation.ui.theme.RallyTheme

class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = values().toList()
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = RallyScreen.fromRoute(
            backStackEntry.value?.destination?.route
        )
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(navController = navController,
                startDestination = Overview.name,
                modifier = Modifier.padding(innerPadding)) {
                composable(Overview.name){
                    OverviewBody(
                        onClickSeeAllAccounts = {navController.navigate(Accounts.name)},
                        onClickSeeAllBills = {navController.navigate(Bills.name)}
                    )
                }
                composable(Bills.name){
                    BillsBody(bills = UserData.bills)
                }
                composable(Accounts.name){
                    AccountsBody(accounts = UserData.accounts)
                }
            }
        }
    }
}