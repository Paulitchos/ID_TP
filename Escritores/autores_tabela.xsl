<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <h2>Autores</h2>
  <table border="1">
    <tr>
      <th>Nome</th>
      <th>Fotografia</th>
    </tr>
    <xsl:for-each select="autores/autor">
    <tr>
      <td><xsl:value-of select="nome"/></td>
      <td><img src="{foto}" alt="{nome}"/></td>
    </tr>
    </xsl:for-each>
  </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>
