<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<xsl:for-each select="escritores/escritor">
					<h2>
						<xsl:value-of select="nome"/>
					</h2>
					<ul>
						<xsl:for-each select="obras/dependency">
							<li>
								<xsl:value-of select="artifactId"/>
							</li>
						</xsl:for-each>
					</ul>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>