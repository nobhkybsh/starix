package com.y9vad9.starix.bot.fsm.admin.settings.club_settings.club_rules

import com.y9vad9.starix.bot.fsm.FSMState
import com.y9vad9.starix.bot.fsm.admin.AdminMainMenuState
import com.y9vad9.starix.bot.fsm.admin.settings.club_settings.AdminViewClubSettingsState
import com.y9vad9.starix.bot.fsm.components.language_picker.LanguagePickerComponentState
import com.y9vad9.starix.bot.fsm.getCurrentStrings
import com.y9vad9.starix.core.brawlstars.entity.club.value.ClubTag
import com.y9vad9.starix.core.system.entity.value.LanguageCode
import com.y9vad9.starix.core.system.usecase.settings.admin.club.GetClubSettingsUseCase
import dev.inmo.tgbotapi.extensions.api.send.send
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContextWithFSM
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.waitText
import dev.inmo.tgbotapi.extensions.utils.types.buttons.replyKeyboard
import dev.inmo.tgbotapi.types.IdChatIdentifier
import dev.inmo.tgbotapi.types.buttons.reply.simpleReplyButton
import dev.inmo.tgbotapi.utils.row
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("AdminViewClubRulesSettingState")
@Serializable
data class AdminViewClubRulesSettingState(
    override val context: IdChatIdentifier,
    val clubTag: ClubTag,
    val languageCode: LanguageCode? = null,
) : FSMState<AdminViewClubRulesSettingState.Dependencies> {
    override suspend fun BehaviourContext.before(
        previousState: FSMState<*>,
        dependencies: Dependencies,
    ): FSMState<*> = with(dependencies) {
        val strings = getCurrentStrings(context)

        return when (val result = getClubSettings.execute(clubTag)) {
            GetClubSettingsUseCase.Result.ClubNotFound -> {
                bot.send(
                    chatId = context,
                    text = strings.clubNotFoundMessage,
                )
                AdminMainMenuState(context)
            }

            is GetClubSettingsUseCase.Result.Success -> {
                val locale = languageCode ?: result.clubSettings.defaultLanguage
                bot.send(
                    chatId = context,
                    entities = strings.admin.settings.clubRulesMessage(result.clubSettings.clubRules[locale], locale),
                    replyMarkup = replyKeyboard {
                        row(simpleReplyButton(strings.changeOption))
                        row(simpleReplyButton(strings.admin.settings.forAnotherLocaleChoice))
                        row(simpleReplyButton(strings.goBackChoice))
                    },
                )
                this@AdminViewClubRulesSettingState
            }
        }
    }

    override suspend fun BehaviourContextWithFSM<in FSMState<*>>.process(
        dependencies: Dependencies,
    ): FSMState<*> = with(dependencies) {
        val strings = getCurrentStrings(context)

        return when (waitText().first().text) {
            strings.changeOption -> AdminChangeContactInfoSettingState(context, clubTag, languageCode)
            strings.goBackChoice -> AdminViewClubSettingsState(context, clubTag)
            strings.admin.settings.forAnotherLocaleChoice ->
                LanguagePickerComponentState(context, callback = LanguagePickerToClubRulesCallback(clubTag))
            else -> {
                bot.send(
                    chatId = context,
                    text = strings.invalidChoiceMessage,
                )
                this@AdminViewClubRulesSettingState
            }
        }
    }

    @SerialName("LanguagePickerToClubRulesCallback")
    @Serializable
    private data class LanguagePickerToClubRulesCallback(
        val clubTag: ClubTag,
    ) : LanguagePickerComponentState.Callback {
        override fun navigateBack(context: IdChatIdentifier): FSMState<*> {
            return AdminViewContactInfoSettingState(context, clubTag)
        }

        override fun navigateForward(context: IdChatIdentifier, code: LanguageCode): FSMState<*> {
            return AdminViewContactInfoSettingState(context, clubTag, code)
        }
    }

    interface Dependencies : FSMState.Dependencies {
        val getClubSettings: GetClubSettingsUseCase
    }
}