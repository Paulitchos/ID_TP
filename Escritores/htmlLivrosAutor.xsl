<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html"/>
<xsl:template match="/">
  <html>
  <body>
    <h2>Livros do autor</h2>
    <table border="1">
      <tr>
        <th>Foto</th>
      </tr>
      <xsl:for-each select="livrosAutor/foto">
      <tr>
        <td><img src="{.}" alt="Foto do livro"/></td>
      </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>