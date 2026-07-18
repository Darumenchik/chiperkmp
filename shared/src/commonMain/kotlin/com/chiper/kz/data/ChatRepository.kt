package com.chiper.kz.data

import com.chiper.kz.utils.PlatformTime
import com.chiper.kz.model.Chat
import com.chiper.kz.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlin.random.Random

class ChatRepository {

    private val _chats = MutableStateFlow(demoChats())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _messages = MutableStateFlow(demoMessages())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    fun getMessagesForChat(chatId: String): List<Message> {
        return _messages.value.filter { it.chatId == chatId }.sortedBy { it.timestamp }
    }

    fun getChatById(chatId: String): Chat? {
        return _chats.value.find { it.id == chatId }
    }

    suspend fun sendMessage(chatId: String, text: String) {
        val message = Message(
            id = "msg_${PlatformTime.currentTimeMillis()}",
            chatId = chatId,
            senderId = "user1",
            text = text,
            timestamp = PlatformTime.currentTimeMillis(),
            isRead = false,
            isSentByMe = true
        )
        _messages.value = _messages.value + message
        _chats.value = _chats.value.map {
            if (it.id == chatId) it.copy(lastMessage = text, lastMessageTime = PlatformTime.currentTimeMillis())
            else it
        }

        delay(1500 + (Random.nextDouble() * 2000).toLong())

        val replies = listOf(
            "Понял, спасибо!",
            "Интересно, расскажи больше",
            "Хорошо, договорились!",
            "Я как раз думал об этом",
            "Отличная идея!",
            "Да, согласен",
            "Сейчас занят, напишу позже",
            "Класс!",
            "Окей",
            "Супер!"
        )
        val reply = Message(
            id = "msg_${PlatformTime.currentTimeMillis()}",
            chatId = chatId,
            senderId = _chats.value.find { it.id == chatId }?.companionId ?: "user2",
            text = replies.random(),
            timestamp = PlatformTime.currentTimeMillis(),
            isRead = false,
            isSentByMe = false
        )
        _messages.value = _messages.value + reply
        _chats.value = _chats.value.map {
            if (it.id == chatId) it.copy(
                lastMessage = reply.text,
                lastMessageTime = PlatformTime.currentTimeMillis()
            )
            else it
        }
    }

    fun markChatAsRead(chatId: String) {
        _chats.value = _chats.value.map {
            if (it.id == chatId) it.copy(unreadCount = 0) else it
        }
    }

    private fun demoChats() = listOf(
        Chat(
            id = "chat1",
            name = "Мария Иванова",
            avatarUrl = "",
            lastMessage = "Привет! Как дела с проектом?",
            lastMessageTime = PlatformTime.currentTimeMillis() - 120000,
            unreadCount = 2,
            isOnline = true,
            companionId = "user2"
        ),
        Chat(
            id = "chat2",
            name = "Дмитрий Козлов",
            avatarUrl = "",
            lastMessage = "Отправил тебе документы на почту",
            lastMessageTime = PlatformTime.currentTimeMillis() - 3600000,
            unreadCount = 0,
            isOnline = false,
            companionId = "user3"
        ),
        Chat(
            id = "chat3",
            name = "Анна Сидорова",
            avatarUrl = "",
            lastMessage = "Увидимся завтра в 15:00!",
            lastMessageTime = PlatformTime.currentTimeMillis() - 7200000,
            unreadCount = 1,
            isOnline = true,
            companionId = "user4"
        ),
        Chat(
            id = "chat4",
            name = "Команда Chiper",
            avatarUrl = "",
            lastMessage = "Релиз запланирован на пятницу",
            lastMessageTime = PlatformTime.currentTimeMillis() - 14400000,
            unreadCount = 5,
            isOnline = false,
            companionId = "team"
        ),
        Chat(
            id = "chat5",
            name = "Елена Волкова",
            avatarUrl = "",
            lastMessage = "Спасибо за помощь! 🙏",
            lastMessageTime = PlatformTime.currentTimeMillis() - 86400000,
            unreadCount = 0,
            isOnline = false,
            companionId = "user5"
        ),
        Chat(
            id = "chat6",
            name = "Игорь Новиков",
            avatarUrl = "",
            lastMessage = "Посмотри новый PR в репозитории",
            lastMessageTime = PlatformTime.currentTimeMillis() - 172800000,
            unreadCount = 0,
            isOnline = true,
            companionId = "user6"
        )
    )

    private fun demoMessages() = listOf(
        Message("m1", "chat1", "user2", "Привет! Как дела с проектом?", PlatformTime.currentTimeMillis() - 180000, false, false),
        Message("m2", "chat1", "user2", "Мы должны закончить MVP до пятницы", PlatformTime.currentTimeMillis() - 170000, false, false),
        Message("m3", "chat1", "user1", "Привет! Всё хорошо, работаю над фронтендом", PlatformTime.currentTimeMillis() - 120000, true, true),
        Message("m4", "chat1", "user1", "Думаю, успеем к пятнице", PlatformTime.currentTimeMillis() - 110000, true, true),

        Message("m5", "chat2", "user3", "Привет, нужна твоя помощь с API", PlatformTime.currentTimeMillis() - 7200000, true, false),
        Message("m6", "chat2", "user1", "Конечно, что нужно?", PlatformTime.currentTimeMillis() - 7100000, true, true),
        Message("m7", "chat2", "user3", "Отправил тебе документы на почту", PlatformTime.currentTimeMillis() - 3600000, true, false),

        Message("m8", "chat3", "user4", "Давай встретимся завтра?", PlatformTime.currentTimeMillis() - 10800000, true, false),
        Message("m9", "chat3", "user1", "Да, во сколько?", PlatformTime.currentTimeMillis() - 10700000, true, true),
        Message("m10", "chat3", "user4", "Увидимся завтра в 15:00!", PlatformTime.currentTimeMillis() - 7200000, false, false),

        Message("m11", "chat4", "team", "Ребят, напоминаю про стендап в 10:00", PlatformTime.currentTimeMillis() - 28800000, true, false),
        Message("m12", "chat4", "team", "Обновил задачи в Jira", PlatformTime.currentTimeMillis() - 21600000, true, false),
        Message("m13", "chat4", "team", "Релиз запланирован на пятницу", PlatformTime.currentTimeMillis() - 14400000, false, false),

        Message("m14", "chat5", "user5", "Помоги с настройкой CI/CD", PlatformTime.currentTimeMillis() - 172800000, true, false),
        Message("m15", "chat5", "user1", "Готово, посмотри в репозитории", PlatformTime.currentTimeMillis() - 86400000, true, true),
        Message("m16", "chat5", "user5", "Спасибо за помощь! 🙏", PlatformTime.currentTimeMillis() - 86300000, true, false),

        Message("m17", "chat6", "user6", "Есть проблемы с тестами", PlatformTime.currentTimeMillis() - 259200000, true, false),
        Message("m18", "chat6", "user1", "Попробуй обновить зависимости", PlatformTime.currentTimeMillis() - 172800001, true, true),
        Message("m19", "chat6", "user6", "Посмотри новый PR в репозитории", PlatformTime.currentTimeMillis() - 172800000, true, false)
    )
}
