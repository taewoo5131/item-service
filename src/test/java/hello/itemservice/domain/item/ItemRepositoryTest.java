package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    public void afterEach() {
        itemRepository.clearStroe();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA" , 10000,10);
        //when
        Item saveItem = itemRepository.save(item);
        //then
        Assertions.assertThat(item).isEqualTo(saveItem);
    }
    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1" , 10000,10);
        Item item2 = new Item("item2" , 20000,20);
        //when
        itemRepository.save(item1);
        itemRepository.save(item2);
        List<Item> all = itemRepository.findAll();
        //then
        Assertions.assertThat(all.size()).isEqualTo(2);
    }
    @Test
    void updateItem() {
        //given
        Item item = new Item("itemA" , 10000,10);
        itemRepository.save(item);
        System.out.println("beforeItem " + item.getItemName());
        //when
        itemRepository.update(item.getId() , new Item("updateItem" , 20000 , 20));
        //then
        System.out.println("updateItem " + item.getItemName());
    }
}