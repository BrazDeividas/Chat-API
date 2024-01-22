package com.api.chat.repository;

import com.api.chat.entity.Message;
import com.api.chat.tables.public_.tables.records.MessagesRecord;
import jakarta.persistence.EntityNotFoundException;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.api.chat.tables.public_.tables.Messages.MESSAGES;
@Repository
public class JooqMessageRepository implements MessageRepository {
    private static final Logger logger = LoggerFactory.getLogger(JooqMessageRepository.class);

    private final DSLContext create;
    @Autowired
    public JooqMessageRepository(DSLContext create) {
        this.create = create;
    }

    @Transactional
    @Override
    public Message add(Message entry) {
        logger.info("Adding new message: {}", entry);
        MessagesRecord persisted = create.insertInto(MESSAGES)
                .set(createRecord(entry))
                .returning()
                .fetchOne();

        Message returned = convertQueryResultToModelObject(persisted);

        logger.info("Added {} message entry", returned);

        return returned;
    }

    @Transactional
    @Override
    public Message delete(Integer id) {
        logger.info("Deleting message entry by id: {}", id);

        Message deleted = findById(id);

        int deletedRecordCount = create.delete(MESSAGES)
                .where(MESSAGES.MESSAGE_ID.equal(id))
                .execute();

        logger.debug("Deleted {} message entries", deletedRecordCount);
        logger.info("Returning deleted message entry: {}", deleted);

        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public Message findById(Integer id) {
        logger.info("Finding message entry by id: {}", id);

        MessagesRecord queryResult = create.selectFrom(MESSAGES)
                .where(MESSAGES.MESSAGE_ID.equal(id))
                .fetchOne();

        logger.debug("Got result: {}", queryResult);

        if (queryResult == null) {
            throw new EntityNotFoundException("No message entry found with id: " + id);
        }

        return convertQueryResultToModelObject(queryResult);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Message> findAll() {
        logger.info("Finding all message entries.");

        List<MessagesRecord> queryResults = create.select()
                .from(MESSAGES)
                .orderBy(MESSAGES.CREATED_AT.desc())
                .fetchInto(MessagesRecord.class);

        List<Message> messages = convertQueryResultsToModelObjects(queryResults);

        logger.info("Found {} message entries", messages.size());

        return messages;
    }

    @Transactional
    @Override
    public Message update(Message messageEntry) {
        logger.info("Updating message: {}", messageEntry);

        MessagesRecord updatedRecordCount = create.update(MESSAGES)
                .set(MESSAGES.CONTENT, messageEntry.getContent())
                .set(MESSAGES.SENDER_ID, messageEntry.getSenderId())
                .where(MESSAGES.MESSAGE_ID.equal(messageEntry.getId()))
                .returning()
                .fetchOne();

        logger.debug("Updated {} message entry.", updatedRecordCount);

        return convertQueryResultToModelObject(updatedRecordCount);
    }

    private MessagesRecord createRecord(Message entry) {
        MessagesRecord record = new MessagesRecord();
        record.setContent(entry.getContent());
        record.setCreatedAt(entry.getCreatedAt());
        record.setSenderId(entry.getSenderId());

        return record;
    }

    private Message convertQueryResultToModelObject(MessagesRecord queryResult) {
        return new Message(
                queryResult.getMessageId(),
                queryResult.getContent(),
                queryResult.getSenderId(),
                queryResult.getCreatedAt()
        );
    }

    private List<Message> convertQueryResultsToModelObjects(List<MessagesRecord> queryResults) {
        List<Message> messages = new ArrayList<>();

        for (MessagesRecord queryResult :queryResults) {
            Message message = convertQueryResultToModelObject(queryResult);
            messages.add(message);
        }

        return messages;
    }
}
