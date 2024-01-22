package com.api.chat.repository;

import com.api.chat.entity.User;
import com.api.chat.tables.public_.enums.RoleName;
import com.api.chat.tables.public_.tables.records.UsersRecord;
import jakarta.persistence.EntityNotFoundException;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.api.chat.tables.public_.tables.Users.USERS;
@Repository
public class JooqUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(JooqUserRepository.class);

    private final DSLContext create;

    @Autowired
    public JooqUserRepository(DSLContext create) {
        this.create = create;
    }

    @Transactional
    @Override
    public User add(User entry) {
        logger.info("Adding new user: {}", entry);
        UsersRecord persisted = create.insertInto(USERS)
                .set(createRecord(entry))
                .returning()
                .fetchOne();

        User returned = convertQueryResultToModelObject(persisted);

        logger.info("Added {} user entry", returned);

        return returned;
    }

    @Transactional
    @Override
    public User delete(Integer id) {
        logger.info("Deleting user entry by id: {}", id);

        User deleted = findById(id);

        int deletedRecordCount = create.delete(USERS)
                .where(USERS.USER_ID.equal(id))
                .execute();

        logger.debug("Deleted {} user entries", deletedRecordCount);
        logger.info("Returning deleted user entry: {}", deleted);

        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        logger.info("Finding user entry by id: {}", id);

        UsersRecord queryResult = create.selectFrom(USERS)
                .where(USERS.USER_ID.equal(id))
                .fetchOne();

        logger.debug("Got result: {}", queryResult);

        if (queryResult == null) {
            throw new EntityNotFoundException("No user entry found with id: " + id);
        }

        return convertQueryResultToModelObject(queryResult);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        logger.info("Finding user entry by username: {}", username);

        UsersRecord queryResult = create.selectFrom(USERS)
                .where(USERS.USERNAME.equal(username))
                .fetchOne();

        logger.debug("Got result: {}", queryResult);

        if (queryResult == null) {
            throw new EntityNotFoundException("No user entry found with username: " + username);
        }

        return convertQueryResultToModelObject(queryResult);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        logger.info("Finding all user entries.");

        List<UsersRecord> queryResults = create.selectFrom(USERS).fetchInto(UsersRecord.class);

        List<User> users = convertQueryResultsToModelObjects(queryResults);

        logger.info("Found {} user entries", users.size());

        return users;
    }

    @Transactional
    @Override
    public User update(User userEntry) {
        logger.info("Updating user: {}", userEntry);

        int updatedRecordCount = create.update(USERS)
                .set(USERS.USERNAME, userEntry.getUsername())
                .set(USERS.PASSWORD, userEntry.getPassword())
                .where(USERS.USER_ID.equal(userEntry.getId()))
                .execute();

        logger.debug("Updated {} user entry.", updatedRecordCount);

        return userEntry;
    }

    private UsersRecord createRecord(User entry) {
        LocalDateTime time = LocalDateTime.now();
        UsersRecord record = new UsersRecord();
        record.setUsername(entry.getUsername());
        record.setPassword(entry.getPassword());
        record.setRole(RoleName.values()[entry.getRole().ordinal()]);
        record.setCreatedAt(LocalDateTime.from(time));

        return record;
    }

    private User convertQueryResultToModelObject(UsersRecord queryResult) {
        return new User(
                queryResult.getUserId(),
                queryResult.getUsername(),
                queryResult.getPassword(),
                com.api.chat.entity.role.RoleName.values()[queryResult.getRole().ordinal()],
                queryResult.getCreatedAt()
        );
    }

    private List<User> convertQueryResultsToModelObjects(List<UsersRecord> queryResults) {
        List<User> users = new ArrayList<>();

        for (UsersRecord queryResult :queryResults) {
            User user = convertQueryResultToModelObject(queryResult);
            users.add(user);
        }

        return users;
    }
}
