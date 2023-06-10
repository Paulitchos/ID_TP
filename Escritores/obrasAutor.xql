declare namespace ns1="http://maven.apache.org/POM/4.0.0";

let $autorNome := "Oscar Wilde" (: trocar pelo nome do autor desejado :)
let $escritores := doc("escritores.xml")/escritores/escritor
let $obras := doc("obras.xml")/ns1:project/dependencies/dependency

return
    <html>
        <body>
            {
                for $escritor in $escritores
                where $escritor/nome = $autorNome
                let $id := $escritor/@id
                let $obra := $obras[groupId="id" and artifactId=$id]
                return
                    <div>
                        <h2>{ $escritor/nome/text() }</h2>
                        <p>{ $escritor/datanascimento/text() }</p>
                        <h3>Obras:</h3>
                        <ul>
                            { for $obra in $obras return <li>{ $obra/artifactId/text() }</li> }
                        </ul>
                    </div>
            }
        </body>
    </html>
