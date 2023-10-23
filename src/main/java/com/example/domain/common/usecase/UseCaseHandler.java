package com.example.domain.common.usecase;

import com.example.domain.common.model.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
