import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clearChat()
    }

    @Test
    fun addMessage() {

        val expect = Unit
        val result = ChatService.addMessage(1, Message("1/1"))

        assertEquals(expect, result)
    }

    @Test
    fun getUnreadChatsCount() {

        val expect = 0
        val result = ChatService.getUnreadChatsCount()

        assertEquals(expect, result)
    }

    @Test
    fun getMessages() {
        ChatService.addMessage(1, Message("1/1"))
        ChatService.addMessage(1, Message("1/2"))
        ChatService.addMessage(1, Message("1/3"))
        ChatService.addMessage(2, Message("2/1"))
        ChatService.addMessage(3, Message("3/1"))
        ChatService.addMessage(3, Message("3/2"))

        val expect = listOf(Message("3/1", read=true, deleted=false), Message("3/2", read = true, deleted = false))
        val result = ChatService.getMessages(3, 2)

        assertEquals(expect, result)
    }

    @Test
    fun getLastMessages() {
        ChatService.addMessage(1, Message("1/1"))
        ChatService.addMessage(1, Message("1/2"))
        ChatService.addMessage(1, Message("1/3"))
        ChatService.addMessage(2, Message("2/1"))
        ChatService.addMessage(3, Message("3/1"))
        ChatService.addMessage(3, Message("3/2"))

        val expect = listOf("1/3", "2/1", "3/2")
        val result = ChatService.getLastMessages()

        assertEquals(expect, result)
    }

    @Test
    fun getChats() {

        ChatService.addMessage(1, Message("1/1"))
        ChatService.addMessage(1, Message("1/2"))
        ChatService.addMessage(1, Message("1/3"))
        ChatService.addMessage(2, Message("2/1"))
        ChatService.addMessage(3, Message("3/1"))
        ChatService.addMessage(3, Message("3/2"))

        val expect = mapOf(
            1 to Chat(
                listOf(Message("1/1", read = false, deleted = false),
                    Message("1/2", read = false, deleted = false),
                    Message("1/3", read = false, deleted = false)) as MutableList<Message>
            ),
            2 to Chat(listOf(Message("2/1", read = false, deleted = false)) as MutableList<Message>),
            3 to Chat(listOf(Message("3/1", read = false, deleted = false),
                Message("3/2", read=false, deleted=false)) as MutableList<Message>))

        val result = ChatService.getChats()

        assertEquals(expect, result)
    }

    @Test
    fun deleteMessage() {

        ChatService.addMessage(1, Message("1/1"))

        val expect = Unit
        val result = ChatService.deleteMessage(1)

        assertEquals(expect, result)
    }

    @Test
    fun deleteChat() {
        ChatService.addMessage(1, Message("1/1"))

        val expect = Unit
        val result = ChatService.deleteChat(1)

        assertEquals(expect, result)
    }
}