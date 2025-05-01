package hernanbosqued.frontend.usecase.auth

import hernanbosqued.domain.UserData

interface DesktopAuthUseCase : AuthUseCase {
    suspend fun openPageAndWaitForResponse(): UserData
}
