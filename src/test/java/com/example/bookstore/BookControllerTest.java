package com.example.bookstore;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testGetAllBooks() throws Exception {
        // Mock data
        Book book = new Book();
        book.setId(1);
        book.setTitle("Book title 1");
        book.setAuthor("Book author 1");
        book.setPublishedDate(new Date(1925, 4, 10));
        book.setGenre("Book genre");

        Mockito.when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        // Test GET /books
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book title 1"))
                .andExpect(jsonPath("$[0].author").value("Book author 1"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddBook() throws Exception {
        // Mock data
        Book book = new Book();
        book.setId(1);
        book.setTitle("Book title 2");
        book.setAuthor("Book author 2");

        Mockito.when(bookService.addBook(any(Book.class))).thenReturn(book);

        // Test POST /books
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Book title 2"))
                .andExpect(jsonPath("$.author").value("Book author 2"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testUpdateBook() throws Exception {
        // Mock data
        Book updatedBook = new Book();
        updatedBook.setId(1);
        updatedBook.setTitle("Book title 3");
        updatedBook.setAuthor("Book author 3");

        Mockito.when(bookService.updateBook(anyInt(), any(Book.class))).thenReturn(updatedBook);

        // Test PUT /books/{id}
        mockMvc.perform(put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book title 3"))
                .andExpect(jsonPath("$.author").value("Book author 3"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testDeleteBook() throws Exception {
        // No need to mock the delete service, as it returns void
        Mockito.doNothing().when(bookService).deleteBook(anyInt());

        // Test DELETE /books/{id}
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());
    }
}
