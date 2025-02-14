package com.keniding.sanlove.data.model.register

import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.domain.enums.RegistrationStep

data class RegistrationState(
    val registrationStep: RegistrationStep = RegistrationStep.INITIAL,
    val temporaryCode: String? = null,
    val profileId: String? = null,
    val partner1: Partner? = null,
    val partner2: Partner? = null,
    val error: String? = null
)
