package com.y9vad9.gamerslegion

import com.y9vad9.gamerslegion.brawlstars.entity.Member
import com.y9vad9.gamerslegion.database.MemberInfo
import com.y9vad9.gamerslegion.usecase.GetBSStatsUseCase
import dev.inmo.tgbotapi.types.message.textsources.TextSource
import dev.inmo.tgbotapi.types.message.textsources.link
import dev.inmo.tgbotapi.utils.bold
import dev.inmo.tgbotapi.utils.buildEntities
import dev.inmo.tgbotapi.utils.italic

object Messages {
    val HELLO_MESSAGE = buildEntities("") {
        +"\uD83E\uDD16 Добро пожаловать в чат-бот клуба «Gamer's Legion»!\n\n" +
            "С чем могу тебе помочь?"
    }

    val I_WANT_TO_JOIN_ANSWER = buildEntities("") {
        +"\uD83E\uDD16 Отлично! " +
            "Я буду сопровождать тебя всё время пока будешь в нашем клубе – напоминать о событиях, следить за твоей активностью и много чего другого!\n\n" +
            bold("Каждый игрок в нашем клубе должен находится в нашем чате") + ", так что если это твой случай, то я тебе помогу с этим. \uD83D\uDE0A\n\n" +
            "Перед тем, как я добавлю тебя в наш чат, " + bold("пожалуйста, предоставь свой игровой тэг") +
            " (например: " + italic("#123ABCD") + ").\n\n" +
            "Чтобы найти свой тэг, открой профиль в игровом меню (кнопка в левом верхнем углу экрана). Тэг игрока находится под изображением твоего профиля."
    }

    val CLUB_RULES_MESSAGE = buildEntities("") {
        bold("Основные правила клуба:\n\n") +
            "\t1) АФК более 3 дней без предупреждения — исключение из клуба.\n" +
            "\t2) Не сыграл мегакопилку без уважительной причины — исключение.\n" +
            "\t3) В мегакопилке необходимо выиграть минимум 6 раз, иначе — исключение.\n" +
            "\t4) В конце сезона рангового боя у каждого участника должен быть ранг не ниже «Алмаз 1». \n" +
            "\t5) Соблюдайте уважение к руководству клуба.\n" +
            "\t6) Один участник — один аккаунт в клубе. Исключения возможны, но по согласованию.\n" +
            "\t7) Нельзя иметь пассажиров\uD83E\uDDCD\u200D♂\uFE0F на своем аккаунте.\n" +
            "\t8) Каждый сезон трофейной лиги необходимо повышать результат на 1000 кубков до сброса.\n" +
            "\t9) Обязательно включите уведомления от бота — это важно для напоминаний о неактивности и других событий.\n\n" +
            "У нас бывают исключения, однако при преждевременном уведомлении в нашем чате."
    }

    val CHAT_RULES_MESSAGE = buildEntities("") {
        +"Спасибо за понимание, теперь небольшие " + bold("правила для общения в нашем чатике") + ":\n\n" +
            "\t1) Мат в чате допустим, но в пределах разумного.\n" +
            "\t2) Запрещены оскорбления, обсуждение политики и реклама других клубов.\n" +
            "\t3) Продажа аккаунтов — своего или чужого — строго запрещена.\n"
    }

    const val ACCEPT_RULES = "✅ Я согласен"

    val invitationLink = buildEntities("") {
        +"Всё готово! Осталось только перейти к нам в чат – жми кнопку ниже!"
    }

    fun validTagMessage(member: Member) = buildEntities("") {
        +"Мы почти у цели, ${member.name}! Осталось определиться c \uD83D\uDCCF правилами."
    }

    fun youAreNotInClubButCannotJoinMessage() = buildEntities("") {
        +"\uD83E\uDD14 Не могу найти тебя в нашем клубе – ты в нём точно есть?\n\n"
        +"В данный момент, наш клуб переполнен, однако если всё ещё желаешь вступить – это можно обсудить с нашим " + link("президентом клуба", "https://t.me/RRE1EYY") + "."
        +"\n\nКак будешь в клубе, просто напиши свой тэг тут ещё раз!"
    }

    fun youAreNotInClubButCanJoinMessage(availableSeats: Int) = buildEntities("") {
        +"\uD83E\uDD14 Не могу найти тебя в нашем клубе – ты в нём точно есть?\n\n"
        +"Но не переживай! В " + link("нашем клубе", "https://brawlify.com/stats/club/2YJ2RGGVC") + " всё ещё есть $availableSeats свободных мест – присоединяйся!"
        +"\n\nКак будешь в клубе, просто напиши свой тэг тут ещё раз!"
    }

    fun cannotJoinMessage() = buildEntities("") {
        +"В данный момент, наш клуб переполнен, однако если всё ещё желаешь вступить – это можно обсудить с нашим " + link("президентом клуба", "https://t.me/RRE1EYY") + "."
    }

    fun canJoinMessage(availableSeats: Int) = buildEntities("") {
        +"В " + link("нашем клубе", "https://brawlify.com/stats/club/2YJ2RGGVC") + " всё ещё есть $availableSeats свободных мест – ждём тебя!"
    }

    fun adminPanelMessage(info: MemberInfo) = buildEntities("") {
        +"Ты находишься в админ-панеле, выбери что хочешь сделать:"
    }

    fun userPanelMessage(info: MemberInfo) = buildEntities("") {
        +"👋 Привет ${info.name}, ты уже вошёл в систему. Выбери что хочешь сделать:"
    }

    fun afterJoinMessage() = buildEntities("") {
        bold("Не забудь включить на мне уведомления") +": я буду автоматически уведомлять тебя" +
            " в случае твоей неактивности и о многом другом!"
        +"\n\nЧтобы сделать это зайди на мой профиль → три точки → уведомления → включить."
        +"\n\nТак же, у тебя теперь есть следующие опции, как у участника клуба:"
    }

    fun willJoinClubVariant(): String = "\uD83D\uDCCB Я планирую присоединиться"
    fun alreadyInClubVariant(): String = "✅ Я уже в клубе"

    fun instructionToRestart(): String = "Пока на этом всё, если ещё понадоблюсь – просто напиши /start ещё раз."

    fun rateMe() = "\uD83D\uDC51 Моя успеваемость"
    fun readRules() = "📏 Правила"

    fun fullRules(): List<TextSource> {
        return CLUB_RULES_MESSAGE + buildEntities("") {
            +"\n\n"+ bold("Правила в самом чате") +":\n" +
            "\t1) Мат в чате допустим, но в пределах разумного.\n" +
                "\t2) Запрещены оскорбления, обсуждение политики и реклама других клубов.\n" +
                "\t3) Продажа аккаунтов — своего или чужого — строго запрещена.\n"
        }
    }

    fun userJoinedChatMessage(info: MemberInfo, statsResult: GetBSStatsUseCase.Result) = buildEntities("") {
        +"Приветствуем в чате, ${info.name}!"
        if (statsResult is GetBSStatsUseCase.Result.Success) {
            +"\n\n" + bold("Статистика игрока:")
            +"\n\t • \uD83C\uDFC6 Общих трофеев: ${statsResult.player.trophies}"
            +"\n\t • \uD83C\uDFC6 Рекорд общих трофеев: ${statsResult.player.highestTrophies}"
            +"\n\t • \uD83C\uDFC6 Побед в одиночном столкновении: ${statsResult.player.soloVictories}"
            +"\n\t • \uD83C\uDFC6 Побед в в двойном столкновении: ${statsResult.player.duoVictories}"
            +"\n\t • \uD83C\uDFC6 Побед в три-на-три: ${statsResult.player.threeVsThreeVictories}"
        }
        + "\n\nПриятного времяпрепровождения в нашем чате!"
    }
}
