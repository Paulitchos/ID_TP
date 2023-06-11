xquery version "1.0";
let $escritores := doc("escritores.xml")//escritor
return
<relatorio>
{
    for $escritor in $escritores
    return
        <escritor nome="{$escritor/@nomePesquisado}">
            <numeroOcupacoes>{count($escritor/ocupacoes/oc)}</numeroOcupacoes>
            <numeroPremios>{count($escritor/premios/pre)}</numeroPremios>
            <numeroGenerosLiterarios>{count($escritor/generoliterario/gen)}</numeroGenerosLiterarios>
        </escritor>
}
</relatorio>