package de.ma.utils


import io.quarkus.test.common.QuarkusTestResource
import io.vertx.ext.auth.User
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional



@QuarkusTestResource(DatabaseTestResource::class)
abstract class AbstractDatabaseTest {

    abstract var entityManager: EntityManager

}