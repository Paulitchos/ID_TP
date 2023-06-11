xquery version "1.0";
let $obras := doc("obras.xml")//obra
let $sorted-obras := (
  for $x in $obras
  order by xs:decimal($x/preco) descending
  return $x
)
let $top5-obras := subsequence($sorted-obras, 1, 5)
return (
  <topObras>{
    for $x in $top5-obras
    return (
      <obra>
        <titulo>{$x/titulo/text()}</titulo>
        <preco>{$x/preco/text()}</preco>
      </obra>
    )
  }</topObras>
)