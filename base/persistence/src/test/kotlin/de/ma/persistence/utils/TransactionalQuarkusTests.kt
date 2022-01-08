package de.ma.persistence.utils

import io.quarkus.test.junit.QuarkusTest
import javax.enterprise.inject.Stereotype
import javax.transaction.Transactional

@QuarkusTest
@Stereotype
@Transactional
@Retention
@Target(AnnotationTarget.CLASS)
annotation class TransactionalQuarkusTest