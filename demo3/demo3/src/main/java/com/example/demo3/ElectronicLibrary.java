package com.example.demo3;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController //если будут http запросы, то можем обращаться к этому классу
//если url будут совпадать
public class ElectronicLibrary {

    private final List<Book>books = new ArrayList<>();

    //url такой, что букс без ничего - пост запрос с адресом букс, который будет содержать внутри
    //json тело с описанием книги

    @PostMapping("/books") // этот методы будет выхываться в случае пост запросов, кот. вызываются по такой url
                                    //метод вызовется, если отправим  пост запрос на локал хост 8080/books
    public void createBook(@RequestBody Book book){ //как узнает, что нужен именно этот метод- если
        books.add(book); //отправлен запрос по этому url адресу, ожидается, что в теле запроса будет описание книги (id, name) в виде json
    }

   @GetMapping("/books")
    public List<Book>getAllBooks(){//get запрос - гет маппинг
        return books;
    }

    @GetMapping("/books/{id}") //переменную url оборачиваем в фигурне скобки, она меняется
    public Book getBook(@PathVariable String id){ //просим книгу по id, аннотация - чтобы убедиться, что айли
        Book result = null;                         //возьмется именно та, что нам надо
        for(Book book: books){
            if(book.getId().equals(id)){
                return book;

            }
        }
        return result;
    }
}
