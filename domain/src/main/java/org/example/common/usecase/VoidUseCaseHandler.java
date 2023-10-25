package org.example.common.usecase;

import org.example.common.model.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {
    void handle(T useCase);
}
