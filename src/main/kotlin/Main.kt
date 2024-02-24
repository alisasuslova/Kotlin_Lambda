data class Message(val text: String, var read: Boolean = false, var deleted: Boolean = false)
data class Chat(val messages: MutableList<Message> = mutableListOf())

class NoChatException : Exception()

object ChatService {

    private val chats = mutableMapOf<Int, Chat>()

    fun addMessage(user_id: Int, message: Message) =
        chats.getOrPut(user_id) { Chat() }.messages.plusAssign(message.copy())

    fun getUnreadChatsCount() = chats.values.count { chat -> chat.messages.any { !it.read } }

    fun getMessages(user_id: Int, count: Int): List<Message> {
        val chat = chats[user_id] ?: throw NoChatException()
        return chat.messages.takeLast(count)
            .asReversed()
            .asSequence()
            .drop(count)
            .toList()
    }

    fun getLastMessages(): List<String> = chats.values.map { it.messages.lastOrNull()?.text ?: "No Messages" }

    fun getChats(): MutableMap<Int, Chat> {
        return chats
    }

    fun deleteMessage(user_id: Int) {
        val chat = chats[user_id] ?: throw NoChatException()
        chat.messages.onEach { it.deleted = true }
    }

    fun deleteChat(user_id: Int) {
        val chat = chats[user_id] ?: throw NoChatException()
        chat.messages.clear()
    }

    fun clearChat() {
        chats.clear()
    }
}

fun main() {

    ChatService.addMessage(1, Message("1/1"))
    ChatService.addMessage(1, Message("1/2"))
    ChatService.addMessage(1, Message("1/3"))
    ChatService.addMessage(2, Message("2/1"))
    ChatService.addMessage(3, Message("3/1"))
    ChatService.addMessage(3, Message("3/2"))
    println(ChatService.getChats())
    println(ChatService.getUnreadChatsCount())
    println(ChatService.getMessages(3, 2))
    println(ChatService.getChats())
    println(ChatService.getLastMessages())
    println(ChatService.deleteMessage(3))
    println(ChatService.getChats())
    println(ChatService.deleteChat(1))
    println(ChatService.getChats())

}