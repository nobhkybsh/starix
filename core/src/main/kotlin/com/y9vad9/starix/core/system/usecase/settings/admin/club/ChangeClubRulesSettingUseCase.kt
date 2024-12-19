@file:Suppress("DuplicatedCode")

package com.y9vad9.starix.core.system.usecase.settings.admin.club

import com.y9vad9.bcm.core.brawlstars.entity.club.value.ClubTag
import com.y9vad9.bcm.core.common.entity.value.CustomMessage
import com.y9vad9.bcm.core.system.entity.isAdminIn
import com.y9vad9.bcm.core.system.entity.isClubAllowed
import com.y9vad9.bcm.core.system.repository.SettingsRepository
import com.y9vad9.bcm.core.system.usecase.settings.admin.club.ChangeChatRulesSettingUseCase.Result
import com.y9vad9.bcm.core.telegram.entity.value.TelegramUserId

class ChangeClubRulesSettingUseCase(
    private val settingsRepository: SettingsRepository,
) {
    suspend fun execute(
        id: TelegramUserId,
        clubTag: ClubTag,
        message: CustomMessage,
    ): Result {
        if (message.value.isBlank())
            return Result.ShouldNotBeEmpty

        val settings = settingsRepository.getSettings()
        if (!settings.isAdminIn(clubTag, id))
            return Result.NoPermission

        if (!settings.isClubAllowed(clubTag))
            return Result.ClubNotFound

        settingsRepository.setSettings(
            settings.copy(
                allowedClubs = settings.allowedClubs.toMutableMap().apply {
                    put(clubTag, settings.allowedClubs[clubTag]!!.copy(clubRules = message))
                }
            )
        )

        return Result.Success
    }

    sealed interface Result {
        data object ClubNotFound : Result
        data object NoPermission : Result
        data object Success : Result
        data object ShouldNotBeEmpty : Result
    }
}