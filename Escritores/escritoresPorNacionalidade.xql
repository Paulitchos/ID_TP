let $escritores := doc("escritores.xml")/escritores/escritor

return
    <escritores>
        {
            for $nacionalidade in distinct-values($escritores/nacionalidade)
            return
                <nacionalidade nome="{$nacionalidade}">
                    { $escritores[nacionalidade = $nacionalidade] }
                </nacionalidade>
        }
    </escritores>

