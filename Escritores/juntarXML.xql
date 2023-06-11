xquery version "1.0";
let $escritores := doc("escritores.xml")//escritor
let $obras := doc("obras.xml")//obra

return
  <escritores>
  {
    for $escritor in $escritores
    let $id := $escritor/@id
    let $obrasDoEscritor := $obras[@codigoautor = $id]
    return
      <escritor>
        {
          $escritor/*,
          <obras>
            {
              for $obra in $obrasDoEscritor
              return <obra>{$obra/*}</obra>
            }
          </obras>
        }
      </escritor>
  }
  </escritores>