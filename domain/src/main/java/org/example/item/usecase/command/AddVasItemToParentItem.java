package org.example.item.usecase.command;

import org.example.common.model.UseCase;
import org.example.item.model.VasItem;
import org.example.item.model.ParentItem;

public record AddVasItemToParentItem(ParentItem parentItem,
                                     VasItem vasItem) implements UseCase {
}
