package com.example.domain.common.usecase;

import com.example.domain.common.model.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {
    void handle(T useCase);
}
