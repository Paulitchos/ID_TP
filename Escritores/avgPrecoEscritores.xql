xquery version "1.0";

let $escritores := doc("escritores.xml")/escritores
let $obras := doc("obras.xml")/obras

return
  <escritores-with-avg-preco>
  {
    for $escritor in $escritores/escritor
    let $escritorId := $escritor/@id
    let $escritorObras := $obras/obra[@codigoautor = $escritorId]
    let $avgPreco :=
      if (empty($escritorObras)) then "N/A"
      else string(avg($escritorObras/preco))
    order by $avgPreco
    return
      <escritor>
        {$escritor/@id}
        {$escritor/nome}
        <avg-preco>{$avgPreco}</avg-preco>
      </escritor>
  }
  </escritores-with-avg-preco>