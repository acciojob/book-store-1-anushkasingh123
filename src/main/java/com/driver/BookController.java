package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity createBook(@RequestBody Book book){
        book.setId(id);
        bookList.add(book);
        id++;
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity getBookById(@PathVariable("id") String id)
    {
        for(Book book:bookList)
        {
            if(book.getId()==Integer.parseInt(id))
            {
                return new ResponseEntity<>(book,HttpStatus.FOUND);
            }
        }
        return null;
    }
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks()
    {
        return new ResponseEntity<>(bookList,HttpStatus.FOUND);
    }
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam("authorname") String authorname)
    {
        List<Book>list=new ArrayList<>();
        for(Book book:bookList)
        {
            if(book.getAuthor().equals(authorname))
            {
                list.add(book);
            }
        }
        return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBookByGenre(@RequestParam("genre") String genre)
    {
        List<Book>list=new ArrayList<>();
        for(Book book:bookList)
        {
            if(book.getGenre().equals(genre))
            {
                list.add(book);
            }
        }
        return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id") String id)
    {
        int flag=0;
        for(Book book:bookList)
        {
            if(book.getId()==Integer.parseInt(id))
            {
                bookList.remove(book);
                flag=1;
            }
        }
        if(flag==1)
            return new ResponseEntity<>("Books by id deleted successfully",HttpStatus.ACCEPTED);
        return  new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks()
    {
        for(Book book:bookList)
        {
            bookList.remove(book);
        }
        return new ResponseEntity<>("All books deleted successfully",HttpStatus.ACCEPTED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()

    // get request /get-all-books
    // getAllBooks()

    // delete request /delete-all-books
    // deleteAllBooks()

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
}
