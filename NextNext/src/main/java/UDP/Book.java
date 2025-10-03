package UDP;

import java.io.Serializable;

/**

 Tên đầy đủ lớp: UDP.Book
 Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
 Hàm khởi tạo:
 public Book(String id, String title, String author, String isbn, String publishDate)
 Trường dữ liệu: private static final long serialVersionUID = 20251107L

  **/
public class Book implements Serializable {
    public String id;
    public String title;
    public String author;
    public String isbn;
    public String publishDate;
    private static final long serialVersionUID = 20251107L;

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}
