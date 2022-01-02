package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
// @RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA" , 10000 , 10));
        itemRepository.save(new Item("itemB" , 20000 , 20));
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items" , items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId , Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("data",findItem);
        return "basic/item";
    }

//    @GetMapping("/{itemId}")
    public ModelAndView item(@PathVariable Long itemId) {
        Item findItem = itemRepository.findById(itemId);
        ModelAndView mav = new ModelAndView("basic/item").addObject("data",findItem);
        return mav;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.GET)
    public String add() {
        return "basic/addForm";
    }

//    @PostMapping(value = "/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {
        Item item = new Item(itemName,price,quantity);
        Item saveItem = itemRepository.save(item);
        model.addAttribute("data",saveItem);
        return "basic/item";
    }

//    @PostMapping(value = "/add")
    public String addItemV2(
            @ModelAttribute("data") Item item
    ) {
        Item saveItem = itemRepository.save(item);
        // @ModelAttribute의 "item" name 속성이 있으면 아래 addAttribute까지 수행
//        model.addAttribute("data",saveItem);
        return "basic/item";
    }

//    @PostMapping(value = "/add")
    public String addItemV2PRG(
            @ModelAttribute("data") Item item
    ) {
        Item saveItem = itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping(value = "/add")
    public String addItemV2PRG_redirectAttribute(
            @ModelAttribute("data") Item item,
            RedirectAttributes redirectAttributes
    ) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saveItem.getId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId , Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("data",findItem);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String update(@PathVariable Long itemId,@ModelAttribute("data") Item item) {
        itemRepository.update(item.getId() , item);
        return "redirect:/basic/items/{itemId}";
    }
}
