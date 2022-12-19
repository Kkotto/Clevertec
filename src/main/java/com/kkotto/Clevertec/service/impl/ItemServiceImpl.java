package com.kkotto.Clevertec.service.impl;

import com.kkotto.Clevertec.repository.CardRepository;
import com.kkotto.Clevertec.repository.ItemRepository;
import com.kkotto.Clevertec.service.ItemService;
import com.kkotto.Clevertec.service.model.entity.Item;
import com.kkotto.Clevertec.service.model.request.ItemPayDto;
import com.kkotto.Clevertec.service.model.request.PayDto;
import com.kkotto.Clevertec.service.model.response.CashReceiptDto;
import com.kkotto.Clevertec.service.model.response.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CardRepository cardRepository;

    @Transactional
    @Override
    public CashReceiptDto createCashReceipt(PayDto payDto) {
        List<ItemPayDto> itemsPayDto = payDto.getItems();
        List<ItemDto> items = itemsPayDto.stream()
                .map(this::convertToItemDto)
                .toList();
        BigDecimal taxableTotal = countTaxableTotal(items);
        double vatValue = 0.17;
        BigDecimal vatAmount = taxableTotal.multiply(BigDecimal.valueOf(vatValue));
        return CashReceiptDto.builder()
                .payDate(LocalDate.now())
                .payTime(LocalTime.now())
                .shopId(payDto.getShopId())
                .cashierId(payDto.getCashierId())
                .items(items)
                .taxableTotal(taxableTotal)
                .vatAmount(vatAmount)
                .totalForPayment(vatAmount.add(taxableTotal))
                .build();
    }

    public ItemDto createItem(ItemDto itemDto){
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        Item save = itemRepository.save(item);
        return new ItemDto.Builder()
                .addName(save.getName())
                .addPrice(save.getPrice())
                .build();
    }

    private ItemDto convertToItemDto(ItemPayDto itemPayDto) {
        Item item = itemRepository.findById(itemPayDto.getItemId()).get();
        return new ItemDto.Builder()
                .addName(item.getName())
                .addPrice(item.getPrice())
                .addQuantity(itemPayDto.getQuantity())
                .addTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(itemPayDto.getQuantity())))
                .build();
    }

    private BigDecimal countTaxableTotal(List<ItemDto> itemDto){
        return BigDecimal.valueOf(itemDto.stream()
                .map(ItemDto::getTotalPrice)
                .count());
    }
}
