package com.sanzhar.spring.services;

import com.sanzhar.spring.models.Book;
import com.sanzhar.spring.models.Person;
import com.sanzhar.spring.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> getBooks(){
        return booksRepository.findAll();
    }

    @Transactional
    public List <Book> getBooksPageByPage(int page, int itemsPerPage){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List <Book> getBooksSorted(){
        return booksRepository.findAll(Sort.by("year"));
    }

    public List <Book> sortAndPage(int page, int itemsPerPage){
        return booksRepository.findAll(PageRequest.of(page,itemsPerPage,Sort.by("year"))).getContent();
    }



    @Transactional
    public Book getBook(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void remove(int id) {
        booksRepository.deleteById(id);
    }



    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }


    public List <Book> getPersonsBooks(int id) {
        return booksRepository.findByOwnerId(id);
    }

    public boolean hasBooks(int id) {
        if(booksRepository.findByOwnerId(id) == null){
            return false;
        }
        return true;
    }


    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        book.setOwner(null);
        book.setTakenAt(null);
        booksRepository.save(book);

    }


    @Transactional
    public void assign(Person person, int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setOwner(person);
        book.setTakenAt(new Date());
        booksRepository.save(book);
    }


    public List <Book> findByName(String startString) {
        return booksRepository.findByNameContainingIgnoreCase(startString);
    }



}
