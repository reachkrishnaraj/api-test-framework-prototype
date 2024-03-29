package org.example.core.framework;

import org.junit.jupiter.api.extension.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RetryTestSuiteExtension implements TestTemplateInvocationContextProvider {

    private static final String RETRY_COUNT_KEY = "retryCount";

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        int retryCount = getRetryCount(context);
        return Stream.of(invocationContext(retryCount));
    }

    private int getRetryCount(ExtensionContext context) {
        return context.getConfigurationParameter(RETRY_COUNT_KEY)
                .map(Integer::parseInt)
                .orElse(0);
    }

    private TestTemplateInvocationContext invocationContext(int retryCount) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Retry attempt " + invocationIndex;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Stream.of(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType().equals(Integer.class);
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                        return retryCount;
                    }
                }).collect(Collectors.toList());
            }
        };
    }
}
