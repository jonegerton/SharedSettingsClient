package com.sharedsettings.client

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.sharedsettings.client.ui.theme.SharedSettingsClientTheme

class MainActivity : ComponentActivity() {

    private val settingState = mutableStateOf("unset")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val setting by settingState
        val context = this
        setContent {
            SharedSettingsClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.fillMaxWidth().offset(0.dp, 100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        RequestButton(context)
                    }
                    Column(Modifier.fillMaxWidth().offset(0.dp, 200.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Setting: $setting")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val appLinkIntent: Intent = intent
        val appLinkData: Uri? = appLinkIntent.data

        if (appLinkData?.host == "jonegerton.com") {
            settingState.value = appLinkData?.getQueryParameter("value") ?: "missing"
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun RequestButton(context: Context) {
    Button(onClick = {
        try {
            val requestIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://jonegerton.com/request-string"))
            context.startActivity(requestIntent)
        } catch (ex: Exception) {
            println("request error $ex")
        }
    }) {
        Text(text = "Request Setting")
    }
}
