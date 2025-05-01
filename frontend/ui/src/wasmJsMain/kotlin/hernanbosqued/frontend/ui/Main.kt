package hernanbosqued.frontend.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.usecase.auth.di.WasmAuthUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.di.WasmAuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import kotlinx.browser.document
import kotlinx.browser.window
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("Atlanta 1")

    startKoin {
        modules(
            TaskViewModelModule.getModule(),
            RepositoryModule.getModule(BuildKonfig.apiUrl),
            TaskUseCaseModule.getModule(),
            WasmAuthUseCaseModule.getModule(BuildKonfig.clientId, BuildKonfig.desktopRedirectUri, listOf("profile", "email")),
            WasmAuthViewModelModule.getModule(),
        )
    }

    ComposeViewport(document.body!!) {
        App(window.location.search)
    }
}
