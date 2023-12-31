package org.example.command.cart.dto;

import org.example.item.model.Item;
import org.example.item.model.ParentItem;

import java.util.ArrayList;
import java.util.List;

public record ItemResponse(int itemId, int categoryId, int sellerId,
                           double price,
                           List<VasItemResponse> vasItems) {
    public static ItemResponse fromItem(Item item) {
        var vasItemDtoList = new ArrayList<VasItemResponse>();

        if (item instanceof ParentItem parentItem)
            parentItem.getSubItemList().stream()
                    .map(VasItemResponse::fromItem)
                    .forEach(vasItemDtoList::add);

        return new ItemResponse(item.getId(),
                item.getCategoryId(),
                item.getSellerId(),
                item.getPrice(),
                vasItemDtoList);
    }
}
