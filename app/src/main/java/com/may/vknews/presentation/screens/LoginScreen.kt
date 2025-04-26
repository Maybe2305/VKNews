package com.may.vknews.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.may.vknews.R
import com.may.vknews.navigation.Screen
import com.may.vknews.presentation.viewmodel.MainViewModel
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.onetap.common.OneTapStyle
import com.vk.id.onetap.common.button.style.OneTapButtonCornersStyle
import com.vk.id.onetap.common.button.style.OneTapButtonElevationStyle
import com.vk.id.onetap.common.button.style.OneTapButtonSizeStyle
import com.vk.id.onetap.compose.onetap.OneTap
import com.vk.id.onetap.compose.onetap.OneTapTitleScenario

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {

    val initializer = VKIDAuthUiParams.Builder().apply {
        scopes = setOf("wall", "friends", "groups")
        build()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            imageVector = ImageVector.vectorResource(R.drawable.vk_svgrepo_com),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "VK News",
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp,
        )

        Spacer(modifier = Modifier.height(48.dp))

        OneTap(
            modifier = Modifier.width(300.dp),
            onAuth = { oAuth, accessToken ->
                val token = accessToken.token
                Log.d("MyLog", "token: $token")
                onLoginClick()
            },
            onFail = { _, fail ->

            },
            authParams = initializer.build(),
            scenario = OneTapTitleScenario.SignIn,
            signInAnotherAccountButtonEnabled = true,
            style = OneTapStyle.Dark(
                cornersStyle = OneTapButtonCornersStyle.Round,
                sizeStyle = OneTapButtonSizeStyle.MEDIUM_46,
                elevationStyle = OneTapButtonElevationStyle.Custom(8f)
            ),

            )
    }
}