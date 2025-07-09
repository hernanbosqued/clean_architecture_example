package hernanbosqued.backend.presenter

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOUserData

fun IdTask.toDto(): DTOIdTask = DTOIdTask(id, name, description, priority)

fun UserData.toDto(): DTOUserData = DTOUserData(name, email, pictureUrl, idToken, refreshToken, qrCode, isMfaAuthenticated)
