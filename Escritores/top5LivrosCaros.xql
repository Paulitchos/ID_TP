let $books := doc("books.xml")/books/book
order by xs:decimal($books/price) descending
return
<top_books>
  {
    subsequence($books, 1, 5)
  }
</top_books>