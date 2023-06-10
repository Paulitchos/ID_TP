<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>Tabela Escritores</title>
        <style>
          table {
            width: 50%;
            border-collapse: collapse;
          }
          th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
          }
          th {
            background-color: #f2f2f2;
          }
          img {
            max-width: 200px;
            max-height: 200px;
          }
        </style>
      </head>
      <body>
        <h1>Tabela Escritores</h1>
        <table>
          <tr>
            <th>Nome</th>
            <th>Fotos</th>
          </tr>
          <xsl:apply-templates select="//escritor"/>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="escritor">
    <tr>
      <td>
        <xsl:value-of select="nome"/>
      </td>
      <td>
        <xsl:if test="foto">
          <img src="{foto}" alt="{nome}"/>
        </xsl:if>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>