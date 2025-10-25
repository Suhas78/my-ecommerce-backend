package com.demo.ecom_project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.ecom_project.model.Product;
import com.demo.ecom_project.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@RestController

@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;
    
    

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/product/{proid}")
    public ResponseEntity<Product> getProductByID(@PathVariable int proid){

        Product product=service.getProductByID(proid);
        if(product !=null){
        return new ResponseEntity<>(service.getProductByID(proid),HttpStatus.OK);
    }
    else
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    //tp post the products
    @PostMapping("/products")
    public  ResponseEntity<?> postProducts(@RequestPart Product product,@RequestPart MultipartFile imageFile){
        try {
            Product product1=service.postProducts(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //to map the images

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductTd(@PathVariable int productId){
        Product product=service.getProductByID(productId);
        byte[] imageFile=product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }



    // @PutMapping("/product/{id}")
    // public ResponseEntity<String>updateProducts(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imagFile){
    //     Product product1=null;
    //     try {
    //         product1=service.updateProduct(id,product,imagFile);
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    //     }
    //    if(product1!=null){
    //       return new ResponseEntity<>("Updated",HttpStatus.OK);
    //    }
    //    else
    //    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    @PutMapping("/product/{id}")
public ResponseEntity<String> updateProducts(
    @PathVariable int id,
    @RequestPart("product") Product product,
    @RequestPart("imageFile") MultipartFile imageFile
) {
    Product product1 = null;
    try {
        product1 = service.updateProduct(id, product, imageFile);
    } catch (Exception e) {
        return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    if (product1 != null) {
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}



    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable int id){
         Product product=service.getProductByID(id);
         if(product!=null){
            service.deleteProducts(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
         }
         else
         return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);

    }


    // @GetMapping("/products/search")
    // public ResponseEntity<List<Product>> searchProducts(@RequestParam String Keyword){
    //   List<Product> products=service.searchProducts(Keyword);
    //   return new ResponseEntity<>(products,HttpStatus.OK);
    // }

    @GetMapping("/products/search")
public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword) {
    List<Product> products = service.searchProducts(keyword);
    return new ResponseEntity<>(products, HttpStatus.OK);
}




@GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = service.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}