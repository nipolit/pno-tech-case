package com.politaev.pnotechcase

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
        val modules = ApplicationModules.of(PnotechcaseApplication::class.java)
        assertDoesNotThrow { modules.verify() }
        Documenter(modules)
            .writeDocumentation()
            .writeModuleCanvases()
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }

}
