data class Message(val text: String, var read: Boolean = false, var deleted: Boolean = false)
data class Chat(val messages: MutableList<Message> = mutableListOf())

class NoChatException: Exception()

object ChatService {

    private val chats = mutableMapOf<Int, Chat>() // хэш-таблица k-id пользователя, v - список сообщений

    //5, 7
    /*fun addMessage(user_id: Int, message: Message) {
    //перед добавлением проверяем, есть ли вообще такой чат, используем функцию расширения getOrPut()
        //получаем либо существующи видоизмененный, либо создаем новый и возращаем его
        //chats.getOrPut(user_id, {Chat()}).message += message.copy()
        chats.getOrPut(user_id){Chat()}.message += message.copy()

    }*/
    fun addMessage(user_id: Int, message: Message) = chats.getOrPut(user_id){Chat()}.messages.plusAssign(message.copy())


    //1
    fun getUnreadChatsCount() = chats.values.count { chat -> chat.messages.any { !it.read }}
    //fun getUnreadChatsCount() = chats.values.count { it.message.any { !it.read }}
    // проходим по всем значениям chats.values, задаем условие, что хотя бы одно сообщение не прочитано
    // it.message.any { !it.read } - первый it - это Chat, второй it - Message . Первый переименовали

    //4
    fun getMessages(user_id: Int, count: Int): List<Message> {
        //для кого и какое количество сообщений хотим получить
        // chats[user_id].message - может вернуть null, если чата нет, поэтому записывает результат в переменную и выбрасываем исключение
        val chat =  chats[user_id] ?: throw NoChatException()
        //chat.messages.takeLast(count) //берем нужно количество последних сообщений
        // добавляем лямбду, чтобы сделать их прочитанными
        return chat.messages.takeLast(count).onEach { it.read = true }
    }

    //3
    fun getLastMessages(): List<String> = chats.values.map { it.messages.lastOrNull()?.text ?: "No Messages"}
    // используем функцию трансформации map, чтобы взять только последнее сообщение
    //fun getLastMessages(): List<String> = chats.values.map { it.messages.last().text }
    // если элемента в списке нет, выбрасывает исключение, для этого lastOrNull()?.text ?: "No Messages"


    //2
    fun getChats() : MutableMap<Int, Chat> {
        return chats
    }

    //6
    fun deleteMessage(user_id: Int) {
        val chat = chats[user_id] ?: throw NoChatException()
        chat.messages.onEach { it.deleted = true }
    }

    //8
    fun deleteChat(user_id: Int){
        val chat =  chats[user_id] ?: throw NoChatException()
        chat.messages.clear()
    }


    fun print() {
        println(chats)
    }
}

fun main() {
    /*ChatService.addMessage(1, Message("1/1" , true))
    ChatService.addMessage(1, Message("1/2", true))
    ChatService.addMessage(1, Message("1/3", true))
    ChatService.addMessage(2, Message("2/1", true))
    ChatService.addMessage(3, Message("3/1", true))
    ChatService.addMessage(3, Message("3/2"))
    println(ChatService.getChats())
    println(ChatService.getUnreadChatsCount())*/

    ChatService.addMessage(1, Message("1/1"))
    ChatService.addMessage(1, Message("1/2"))
    ChatService.addMessage(1, Message("1/3"))
    ChatService.addMessage(2, Message("2/1"))
    ChatService.addMessage(3, Message("3/1"))
    ChatService.addMessage(3, Message("3/2"))
    println(ChatService.getChats())
    println(ChatService.getUnreadChatsCount())
    println(ChatService.getMessages(3,2))
    println(ChatService.getChats())
    println(ChatService.getLastMessages())
    println(ChatService.deleteMessage(3))
    println(ChatService.getChats())
    println(ChatService.deleteChat(1))
    println(ChatService.getChats())

}