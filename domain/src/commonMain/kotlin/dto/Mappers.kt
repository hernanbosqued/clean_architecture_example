package hernanbosqued.domain.dto

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.UserData

fun IdTask.toDto(): DTOIdTask = DTOIdTask(
    id = id,
    name = name,
    description = description,
    priority = priority
)

fun UserData.toDto(): DTOUserData = DTOUserData(
    userId = userId,
    name = name,
    email = email,
    pictureUrl = pictureUrl,
    idToken = idToken,
    refreshToken = refreshToken,
    totpUri = totpUri,
    totpUriQrCode = totpUriQrCode,
    isMfaAuthenticated = isMfaAuthenticated
)
