package com.politaev.pnotechcase

import com.tngtech.archunit.core.domain.JavaClass
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

@SpringBootTest
class PnotechcaseApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun verifyModularStructure() {
//        val ignoredClasses = JavaClass.Predicates.equivalentTo(PnotechcaseApplication::class.java)
        val ignoredClasses = JavaClass.Predicates.resideInAPackage("com.politaev.pnotechcase.dataimport")
        val modules = ApplicationModules.of(PnotechcaseApplication::class.java, ignoredClasses)
        assertDoesNotThrow { modules.verify() }
        Documenter(modules)
            .writeDocumentation()
            .writeModuleCanvases()
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }

}
