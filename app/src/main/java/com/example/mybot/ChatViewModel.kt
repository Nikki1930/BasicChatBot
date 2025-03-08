package com.example.mybot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel= GenerativeModel(
        modelName = "gemini-1.5-flash-001",
        apiKey = Constants.apikey
    )
    fun SendMessage(question : String){
        viewModelScope.launch{

            try{
                val chat = generativeModel.startChat(
                    history = messageList.map{
                        content(it.role){text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question,"user"))
                messageList.add(MessageModel("typing...","model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()

                messageList.add(MessageModel(response.text.toString(),"model"))
            }
            catch (e : Exception){
                messageList.removeLast()

                messageList.add(MessageModel("Error : "+e.message.toString(),"model"))
            }



        }
    }
}