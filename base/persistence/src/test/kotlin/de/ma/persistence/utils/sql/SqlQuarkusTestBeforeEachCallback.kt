package de.ma.persistence.utils.sql

import de.ma.persistence.utils.sql.SqlFileProcessor.processTargetFile
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import kotlin.reflect.KClass
import kotlin.reflect.full.functions

class SqlQuarkusTestBeforeEachCallback : QuarkusTestBeforeEachCallback {

    override fun beforeEach(context: QuarkusTestMethodContext) = runBlocking(
        Dispatchers.IO
    ) {
        //get the package of the context.testInstance
        var packageName = context.testInstance::class.qualifiedName!!.substringBeforeLast('.')

        //replace the '.' in packageName with '/' and add the prefix 'sql/'
        packageName = "/sql/${packageName.replace('.', '/')}"

        val listOfSql = context.testMethod.getAnnotationsByType(Sql::class.java)
        if (listOfSql.isNotEmpty()) {
            for (sql in listOfSql) {
                val targetFiles = sql.value
                for (targetFile in targetFiles) {
                    //put the package name add the target file name
                    processTargetFile("$packageName/$targetFile")
                }
            }
        }


    }


}