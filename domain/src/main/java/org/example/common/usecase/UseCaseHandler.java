package org.example.common.usecase;

import org.example.common.model.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
