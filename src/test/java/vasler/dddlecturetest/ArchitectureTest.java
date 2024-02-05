package vasler.dddlecturetest;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;
import vasler.dddlecture.Application;

@AnalyzeClasses(packagesOf = Application.class)
public class ArchitectureTest {
    @ArchTest
    public static final ArchRule ddd = JMoleculesDddRules.all();
    @ArchTest
    public static final ArchRule hexagonal = JMoleculesArchitectureRules.ensureHexagonal();

    @ArchTest
    public static final ArchRule gettersOnModelCalledOnlyFromDomainServices = ensureGettersOnModelCalledOnlyFromDomainServices();

    private static ArchRule ensureGettersOnModelCalledOnlyFromDomainServices() {
        return ArchRuleDefinition
            .methods()
            .that()
            .areDeclaredInClassesThat()
            .resideInAnyPackage("..domain.model..")
            .and().haveNameStartingWith("get")
            .and().doNotHaveName("getId")
            .should()
            .onlyBeCalled().byClassesThat().resideInAnyPackage("..domain.service..")
            // TODO -- verify
            .allowEmptyShould(true);
    }
}
