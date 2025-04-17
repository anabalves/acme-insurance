package com.acmeinsurance.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import org.junit.jupiter.api.Test;

class LayersTest {

    private static final String BASE_PACKAGE = "com.acmeinsurance";

    @Test
    void shouldRespectLayeredArchitecture() {
        JavaClasses classes = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);

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
