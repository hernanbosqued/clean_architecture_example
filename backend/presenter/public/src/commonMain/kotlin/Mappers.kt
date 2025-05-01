package hernanbosqued.backend.presenter

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.UserData

fun IdTask.toDto(): DTOIdTask = DTOIdTask(id, name, description, priority)

fun UserData.toDto(): DTOUserData = DTOUserData(name, email, pictureUrl)
