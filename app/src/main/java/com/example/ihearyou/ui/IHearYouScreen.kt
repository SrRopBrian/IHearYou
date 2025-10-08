package com.example.ihearyou.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import com.example.ihearyou.R


@Composable
fun IHearYouScreen(
    ihuViewModel: IHearYouViewModel = viewModel()
) {
    val ihuUiState by ihuViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ihuUiState.screenColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    // check if the permission is granted
                    val permission = Manifest.permission.RECORD_AUDIO
                    if (ContextCompat.checkSelfPermission(
                            context,permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // ToDo
                    } else {
                        // ToDo request permission
                    }
                    ihuViewModel.updateScreenColor()}
            ) {
                Icon(
                    painter = painterResource(R.drawable.black_mic),
                    contentDescription = stringResource(R.string.black_mic)
                )
            }

            Text(
                text = stringResource(R.string.press_start)
            )
        }
    }
}


