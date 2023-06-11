xquery version "1.0";

declare variable $author-id as xs:string external;

<result>
{
  let $author := doc("escritores.xml")//escritor[@id = $author-id]
  let $books := doc("obras.xml")//obra[@codigoautor = $author-id]
  return
  <html>
    <head>
      <title>{data($author/nome)}</title>
    </head>
    <body>
      <h1>{data($author/nome)}</h1>
      <img src="{data($author/foto)}" alt="Foto do autor"/>
      <h2>Livros do autor:</h2>
      {
        for $book in $books
        return
        <div>
          <h3>{data($book/titulo)}</h3>
          <img src="{data($book/capa)}" alt="Capa do livro" style="width: 300px; height: 300px;"/>
        </div>
      }
    </body>
  </html>
}
</result>