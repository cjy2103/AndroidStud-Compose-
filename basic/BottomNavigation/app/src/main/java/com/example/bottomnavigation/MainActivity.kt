package com.example.bottomnavigation

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.ui.navigation.BottomNavItem
import com.example.bottomnavigation.ui.navigation.BottomNavItem.*
import com.example.bottomnavigation.ui.navigation.NavigationHost
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme
import com.example.bottomnavigation.util.SystemUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BottomNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
//    val activity = SystemUtil.findActivity(LocalContext.current)
//    val window = activity.window
//    val view = LocalView.current

//    SystemUtil.sofNavigationBarHide(window = window)
//    SystemUtil.statusbarSetting(window = window, view = view)



    val navController = rememberNavController()
    NavigationHost(navController)


    Spacer(modifier = Modifier.height(50.dp))



    Scaffold(
        bottomBar = { MyBottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationHost(navController = navController)
        }
    }

    }

@Composable
fun MyBottomNavigation(navController: NavController) {

    val items = listOf(
        Djmax,
        Momoi,
        Midori
    )

    BottomNavigation(
        backgroundColor = Color(0x99FFFFFF),
        contentColor = Color.Unspecified,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it){
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                },
                unselectedContentColor = Color.Unspecified
                )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BottomNavigationTheme {
        Greeting()
    }
}