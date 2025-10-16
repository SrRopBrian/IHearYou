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
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalCursorBlinkEnabled
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.startActivity
import com.example.ihearyou.R
import java.util.Locale


@Composable
fun IHearYouScreen(
    ihuViewModel: IHearYouViewModel = viewModel()
) {
    val ihuUiState by ihuViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val packageName = context.packageName
    val activity = context as Activity

    // calling the SpeechRecognizer
    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    // creating the Intent with extra properties
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        }
    }

    // Initialize text to speech
    val ttsHelper = remember { TTSHelper(context) }

    DisposableEffect(Unit) {
        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                ihuViewModel.stopListening()
            }
            override fun onError(error: Int) {
                ihuViewModel.stopListening()
            }

            // get results from the speech recognizer, pass them to the viewModel and update UI
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val spokenText = matches?.firstOrNull()?.lowercase(Locale.getDefault()) ?: ""

                ihuViewModel.updateText(spokenText)

                when (spokenText) {
                    "blue" -> { ttsHelper.speak("Here is the blue screen") }
                    "red" -> { ttsHelper.speak("Here is the red screen") }
                    "" -> { ttsHelper.speak("Please say something") }
                    else -> { ttsHelper.speak("Sorry, I didn't get that.") }
                }

                ihuViewModel.handleRecognitionResult(
                    onUnrecognized = {
                        Toast.makeText(context, "Color not recognized", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(listener)

        onDispose {
            speechRecognizer.destroy()
        }
    }

    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    // permissions request activity
    val permissionsResultActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
                permissions.forEach { permission ->
                    if (result[permission] == false) { // check if permission is declined
                        // if is declined, check if the rationale should be displayed
                        // launch settings if false, display if true
                        if (!shouldShowRequestPermissionRationale(activity, permission)) {
                            ihuViewModel.updateLaunchAppSettings(true)
                        }
                        ihuViewModel.updateShowDialog(true)
                    }
                }
        }
    )

    // User Interface
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
                    // check if the permission(s) is granted
                    // if is granted, record audio, else request for it, etc.
                    permissions.forEach { permission ->
                        if (ContextCompat.checkSelfPermission(
                                context,permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            // ToDo
                        } else { // if permission is not granted, check if rationale has to be shown
                            // if rationale has to be shown update showDialog state
                            if (activity.shouldShowRequestPermissionRationale(permission)) {
                                ihuViewModel.updateShowDialog(true)
                            } else {
                                // if rationale doesn't have to be shown, request for permission
                                permissionsResultActivityLauncher.launch(permissions)
                            }
                        }
                    }
                    ihuViewModel.startListening()
                    speechRecognizer.startListening(recognizerIntent)},
                modifier = Modifier.size(80.dp)) {
                Icon(
                    painter = painterResource(R.drawable.black_mic),
                    tint = if (ihuUiState.isListening) Color.LightGray else Color.Black,
                    contentDescription = stringResource(R.string.black_mic)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = if (ihuUiState.isListening) stringResource(R.string.listening) else stringResource(R.string.press_start),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }

    if (ihuUiState.showDialog) {
        PermissionDialog(
            onDismiss = { ihuViewModel.updateShowDialog(false) },
            onConfirm = {
                ihuViewModel.updateShowDialog(false)

                if (ihuUiState.launchAppSettings) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null)
                    )

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                } else {
                    permissionsResultActivityLauncher.launch(permissions)
                }
            }
        )
    }
}

// dialog to show the rationale for asking for permissions
@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(
                text = "Microphone permission is needed",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = "IHearYou needs access to your Microphone so as to hear your commands."
            )
        }
    )
}