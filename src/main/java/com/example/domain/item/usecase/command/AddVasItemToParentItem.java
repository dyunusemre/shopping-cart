package com.example.domain.item.usecase.command;

import com.example.domain.common.model.UseCase;
import com.example.domain.item.model.VasItem;
import com.example.domain.item.model.ParentItem;

public record AddVasItemToParentItem(ParentItem parentItem,
                                     VasItem vasItem) implements UseCase {
}
