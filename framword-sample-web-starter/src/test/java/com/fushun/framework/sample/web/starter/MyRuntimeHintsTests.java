//package com.fushun.framework.sample.web.starter;
//
//import com.fushun.framework.sample.web.starter.config.PyNativeConfiguration;
//import com.fushun.framework.sample.web.starter.config.PyRuntimeHintsRegistrar;
//import org.junit.jupiter.api.Test;
//import org.springframework.aot.hint.RuntimeHints;
//import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//class MyRuntimeHintsTests {
//
//    @Test
//    void shouldRegisterHints() {
//        RuntimeHints hints = new RuntimeHints();
//        new PyRuntimeHintsRegistrar().registerHints(hints, getClass().getClassLoader());
//        assertThat(RuntimeHintsPredicates.resource().forResource("spy.properties")).accepts(hints);
//    }
//
//}
