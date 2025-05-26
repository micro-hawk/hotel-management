package com.spring.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Item;
import com.spring.entity.Response;
import com.spring.exception.ItemNotFoundException;
import com.spring.service.ItemService;

@RestController
@RequestMapping("/inventory")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    // add item
    @PostMapping("/addItem")
    public Response addItem(@RequestBody Item item) {
        logger.info("Request received to add item: {}", item);
        Item result = itemService.addItem(item);
        if (result != null) {
            logger.info("Item added successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setItem(result)
                    .setMessage("Item Saved Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } else {
            logger.error("Item could not be saved: {}", item);
            Response response = new Response.Builder().setSuccess(false).setItem(item)
                    .setMessage("Item not Saved Successfully").setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // update item
    @PostMapping("/updateItem/{id}")
    public Response updateItemResponse(@RequestBody Item item, @PathVariable int id) {
        return updateItem(item, id);
    }

    @PutMapping("/updateItem/{id}")
    public Response updateItem(@RequestBody Item item, @PathVariable int id) {
        logger.info("Request received to update item with ID: {}", id);
        try {
            Item result = itemService.updateItem(item, id);
            logger.info("Item updated successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setItem(result)
                    .setMessage("Item Saved Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (ItemNotFoundException e) {
            logger.error("Error updating item with ID: {}: {}", id, e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setItem(item).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // delete item
    @GetMapping("/deleteItem/{id}")
    public Response deleteItemResponse(@PathVariable int id) {
        return deleteItem(id);
    }

    @DeleteMapping("/deleteItem/{id}")
    public Response deleteItem(@PathVariable int id) {
        logger.info("Request received to delete item with ID: {}", id);
        try {
            Item item = itemService.deleteItem(id);
            logger.info("Item deleted successfully: {}", item);
            Response response = new Response.Builder().setSuccess(true).setItem(item)
                    .setMessage("Item deleted Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (ItemNotFoundException e) {
            logger.error("Error deleting item with ID: {}: {}", id, e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setItem(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // view all items
    @GetMapping("/getItems")
    public List<Item> getItems() {
        logger.info("Request received to get all items.");
        List<Item> items = itemService.getItems();
        logger.info("Retrieved {} items", items.size());
        return items;
    }

    // get item by id
    @GetMapping("/getItemById/{id}")
    public Item getItemById(@PathVariable int id) {
        logger.info("Request received to get item by ID: {}", id);
        Item item = itemService.getItemById(id);
        if (item != null) {
            logger.info("Item retrieved: {}", item);
        } else {
            logger.warn("Item not found with ID: {}", id);
        }
        return item;
    }
}
