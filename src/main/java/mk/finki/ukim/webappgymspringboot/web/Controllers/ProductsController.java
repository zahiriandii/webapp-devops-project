package mk.finki.ukim.webappgymspringboot.web.Controllers;

import mk.finki.ukim.webappgymspringboot.Model.Product;
import mk.finki.ukim.webappgymspringboot.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = {"/","/products"})
public class ProductsController
{

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProductPage (@RequestParam(required = false) String error , Model model)
    {
        if (error!=null && error.isEmpty())
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        List<Product> products = this.productService.findAll();
        model.addAttribute("products",products);
        model.addAttribute("bodyContent","products-square");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct (@PathVariable Long id)
    {
        this.productService.deleteById(id);
        return "redirect:/products";
    }
    @GetMapping("/edit-form/{id}")
    public String editProductPage (@PathVariable Long id,Model model)
    {
        if (this.productService.findById(id).isPresent()) {
            Product product = this.productService.findById(id).get();

            model.addAttribute("product",product);
            model.addAttribute("bodyContent","edit-product");
            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    public String addProductPage(Model model)
    {
        model.addAttribute("bodyContent","add-product");
        return "master-template";
    }
    @PostMapping("/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam double price,
                              @RequestParam int quantity)
    {
        this.productService.save(name,price,quantity);
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam double price,
                              @RequestParam int quantity)
    {
        this.productService.edit(id,name,price,quantity);
        return "redirect:/products";
    }
}