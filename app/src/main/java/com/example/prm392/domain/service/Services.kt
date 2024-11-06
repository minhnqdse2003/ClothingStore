package com.example.prm392.domain.service

import com.example.prm392.domain.service.MessageService.GetMessageById
import com.example.prm392.domain.service.MessageService.MessageService
import com.example.prm392.domain.service.MessageService.SendMessage
import com.example.prm392.domain.service.NotifyService.GetNotify
import com.example.prm392.domain.service.NotifyService.UpdateStatus

data class Services (
    val getProductDataService: GetProductDataService,
    val getSearchProductDataService: GetSearchProductDataService,
    val getMessageService: GetMessageById,
    val sendMessageService: SendMessage,
    val getNotifyService:GetNotify,
    val updateStatus: UpdateStatus,
)