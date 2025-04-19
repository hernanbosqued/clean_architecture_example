package hernanbosqued.frontend.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import clean_architecture_example.frontend.ui.generated.resources.Res
import clean_architecture_example.frontend.ui.generated.resources.compose_multiplatform
import hernanbosqued.frontend.repository.Repository
import hernanbosqued.frontend.repository.di.RepositoryModule
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.context.startKoin


//import org.koin.core.module.Module
//
//fun getModules(): List<Module> = emptyList<Module>()


@Composable
@Preview
fun App() {
    startKoin { modules(RepositoryModule.getModule()) }

    // Set current Koin instance to Compose context
    KoinContext {
        val repository: Repository = koinInject<Repository>()

        val scope = rememberCoroutineScope()
        var text by remember { mutableStateOf("Loading") }

        scope.launch {
            text = repository.greeting()
        }

        MaterialTheme {
            var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text(text)
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }
        }
    }
}