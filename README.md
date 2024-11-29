# Bookstore

A Spring Boot application providing a RESTful API for managing books. 

### API Description

1. Retrieve All Books
   GET /books: Fetches a list of all books in the collection. No authentication is required to access this endpoint.
2. Add a New Book
   POST /books: Adds a new book to the collection with required details like title, author, and publication date. Authentication is required for this endpoint.
3. Update an Existing Book
   PUT /books/{id}: Updates the details of an existing book identified by its ID. Requires authentication to modify book details.
4. Delete a Book
   DELETE /books/{id}: Deletes a book by its ID from the collection. This operation requires authentication to perform the deletion.

### Build application
```bash
mvn clean install
```

### Run Application
```bash
mvn spring-boot:run
```

### Run test
```bash
mvn test
```

### Access the application at 
```declarative
http://localhost:8080
```