declare namespace ns1="http://maven.apache.org/POM/4.0.0";

let $escritores := doc("escritores.xml")/escritores/escritor
let $obras := doc("obras.xml")/ns1:project

return
    <escritores>
    {
        for $escritor in $escritores
        let $id := $escritor/@id
        let $obra := $obras/dependency[groupId="id" and artifactId=$id]
        return
            <escritor>
            {
                $escritor/*,
                <obras>
                {
                    $obra/*
                }
                </obras>
            }
            </escritor>
    }
    </escritores>
