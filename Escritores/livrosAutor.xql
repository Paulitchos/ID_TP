declare variable $nomeAutor as xs:string external;
let $livros := doc('obras.xml')/obras/livro
let $autor := doc('escritores.xml')/escritores/escritor
where $autor/nome = $nomeAutor
return
<livrosAutor>
    {
        for $livro in $livros
        where $livro/autorId = $autor/@id
        return $livro/foto
    }
</livrosAutor>

