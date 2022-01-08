package com.codinginflow.imagesearchapp.utils

import javax.inject.Inject

interface EntityMapper<EntityMapper,DomainModel> {

    fun mapFromEntity(entity: EntityMapper): DomainModel
    fun mapToEntity(domainModel: DomainModel): EntityMapper
    fun mapFromEntityList(entities: List<EntityMapper>): List<DomainModel>
    fun mapToEntityList(domains: List<DomainModel>): List<EntityMapper>

}

/*

class CheckStatusMapper @Inject constructor() : EntityMapper<ChatMessage, ChatMessageEntity> {

    override fun mapFromEntity(entity: ChatMessage): ChatMessageEntity {
        return ChatMessageEntity(
            entity.message
        )
    }

    override fun mapToEntity(domainModel: ChatMessageEntity): ChatMessage {
        return ChatMessage(
            domainModel.text
        )
    }

    override fun mapFromEntityList(entities: List<ChatMessage>): List<ChatMessageEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<ChatMessageEntity>): List<ChatMessage> {
        return domains.map { mapToEntity(it) }
    }
}*/
