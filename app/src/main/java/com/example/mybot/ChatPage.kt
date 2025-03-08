package com.example.mybot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybot.ui.theme.Purple80
import com.example.mybot.ui.theme.PurpleGrey80
import com.example.mybot.ui.theme.modelColor
import com.example.mybot.ui.theme.userColor
import com.google.ai.client.generativeai.type.content

@Composable
fun ChatPage(modifier : Modifier = Modifier,viewModel: ChatViewModel){

    Column(
        modifier = modifier
    ){
        AppHeader()
        MessageList(modifier = Modifier.weight(1f),
            messageList = viewModel.messageList)
        MessageInput(onMessageSend = {
                viewModel.SendMessage(it)
        })
    }

}

@Composable
fun MessageList(modifier : Modifier = Modifier, messageList : List<MessageModel>) {

    if (messageList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }

    }
    else{
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(modifier = modifier.size(80.dp),
                painter = painterResource(R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = PurpleGrey80,
            )

        }
    }
}


@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel = messageModel.role=="model"

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isModel) modelColor else userColor)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }

            }
        }

    }

}
@Composable
fun MessageInput(onMessageSend : (String) -> Unit){

    var Message by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = Message,
            onValueChange = {
            Message = it
        })
        IconButton(onClick = {
            if(Message.isNotEmpty()){
                onMessageSend(Message)
                Message = ""
            }

        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
        }
    }
}

@Composable
fun AppHeader(){

    Box(
        modifier =  Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
    ){
        Text(
            modifier = Modifier.padding(16.dp),
            text = "MyBot",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}