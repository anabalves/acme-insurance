package com.acmeinsurance.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.Architectures;

import org.junit.jupiter.api.Test;

class LayersTest {

    private static final String BASE_PACKAGE = "com.acmeinsurance";

    @Test
    void shouldRespectLayeredArchitecture() {
        JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);

        LayeredArchitecture architecture = Architectures.layeredArchitecture().consideringAllDependencies()
                .layer("Domain").definedBy(BASE_PACKAGE + ".domain..").layer("Application")
                .definedBy(BASE_PACKAGE + ".application..").layer("Infrastructure")
                .definedBy(BASE_PACKAGE + ".infrastructure..")

                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure").whereLayer("Infrastructure")
                .mayNotBeAccessedByAnyLayer();

        architecture.check(classes);
    }
}
