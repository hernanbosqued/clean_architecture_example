package hernanbosqued.frontend.platform_controller.di

import hernanbosqued.frontend.platform_controller.LoginActions
import hernanbosqued.frontend.platform_controller.impl.DesktopLoginActions
import org.koin.dsl.module

object DesktopPlatformControllerModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<LoginActions> {
            DesktopLoginActions(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                frontendRepository = get(),
            )
        }
    }
}
