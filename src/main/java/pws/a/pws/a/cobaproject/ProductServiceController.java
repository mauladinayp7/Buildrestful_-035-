/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws.a.pws.a.cobaproject;

import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mladi
 */
@RestController
public class ProductServiceController {
    private static Map<String, Product> productRepo = new HashMap<>(); //menggunakan HashMap untuk menyimpan product
    static {
        
        Product honey = new Product(); //membuat produk baru dan memanggil file Product.java
        honey.setId("1"); //membuat id product
        honey.setName("Honey"); //membuat nama product
        honey.setPrice(25000.0);
        honey.setDiscount(0.03);
        honey.setTotal (honey.getPrice()-(honey.getPrice()*honey.getDiscount()/100));
        productRepo.put(honey.getId(), honey); //memasukkan product ke HashMap
        
        Product almond = new Product();//membuat produk baru dan memanggil file Product.java
        almond.setId("2");//membuat id product
        almond.setName("Almond");//membuat nama product
        almond.setPrice (20000.0);
        almond.setDiscount(0.05);
        almond.setTotal (almond.getPrice()-(almond.getPrice()*almond.getDiscount()/100));
        productRepo.put(almond.getId(), almond);//memasukkan product ke HashMap
        
    }
    //get api
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepo.values(),HttpStatus.OK);
    }
    //post api
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        if (productRepo.containsKey(product.getId())){
        return new ResponseEntity<>("id already", HttpStatus.OK);
  
        }else{
            product.setTotal (product.getPrice()-(product.getPrice()*product.getDiscount()/100));
            productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);//popup berhasil meng create
        }
    }
    //put api
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody
    Product product) {
        if (!productRepo.containsKey(id)){
            return new ResponseEntity<>("Id does'not exist", HttpStatus.OK);
        }
        else{
        productRepo.remove(id);
        product.setId(id);
        product.setTotal (product.getPrice()-(product.getPrice()*product.getDiscount()/100));
        productRepo.put(id, product);//memanggil id dan nama product yg akan di update
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);//popup berhasil meng update
        }
    }
    //delete api
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        productRepo.remove(id);//menghapus product menggunakan id
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);//popup berhasil menghapus
    }

    
}
