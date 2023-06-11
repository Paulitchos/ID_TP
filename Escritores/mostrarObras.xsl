<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" indent="yes"/>

  <xsl:key name="authorKey" match="obra" use="@codigoautor"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>Obras</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            margin: 20px;
          }
          h1 {
            text-align: center;
          }
          h2 {
            margin-top: 30px;
          }
          table {
            width: 100%;
            border-collapse: collapse;
          }
          th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
          }
          th {
            background-color: #f2f2f2;
            font-weight: bold;
          }
          img {
            max-width: 150px;
            max-height: 150px;
          }
        </style>
      </head>
      <body>
        <h1>Obras</h1>
        <xsl:apply-templates select="obras/obra[generate-id() = generate-id(key('authorKey', @codigoautor)[1])]">
          <xsl:sort select="preco" data-type="number" order="ascending"/>
        </xsl:apply-templates>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="obra">
    <h2><xsl:value-of select="autor"/></h2>
    <table>
      <tr>
        <th>Titulo</th>
        <th>Foto</th>
        <th>Preço</th>
        <th>Editora</th>
      </tr>
      <xsl:for-each select="key('authorKey', @codigoautor)">
        <xsl:sort select="preco" data-type="number" order="ascending"/>
        <tr>
          <td><xsl:value-of select="titulo"/></td>
          <td><img src="{capa}" alt="Photo"/></td>
          <td><xsl:value-of select="preco"/></td>
          <td><xsl:value-of select="editora"/></td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>
</xsl:stylesheet>
