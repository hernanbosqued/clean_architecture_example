package hernanbosqued.frontend.platform_controller.di

import hernanbosqued.frontend.platform_controller.LoginActions
import hernanbosqued.frontend.usecase.auth.impl.WasmLoginActions
import org.koin.dsl.bind
import org.koin.dsl.module

object WasmPlatformControllerModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<WasmLoginActions> {
            println("Atlanta 2")
            WasmLoginActions(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                frontendRepository = get(),
            )
        }.bind(LoginActions::class)
    }
}
