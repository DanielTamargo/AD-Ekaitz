## CREAR COLECCIÓN

```java
public static void crearColeccion() {
	if (conectar() == null) {
		try {
			System.out.println("La colección '" + nombreColeccion + "' no existe");

			// DOCUMENTACIÓN: https://www.exist-db.org/exist/apps/doc/xmldb
			// Accedemos a la colección general donde crearemos la colección a usar en la práctica
			String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db";
			Class cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.newInstance(); // Instanciar la BD
			DatabaseManager.registerDatabase(database); // Registro del driver
			col = DatabaseManager.getCollection(URI, usu, usuPwd); // Cargar la colección en cuestión

			XPathQueryService servicio;
			servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			//Preparamos la creación
			String crearColeccion = "declare namespace rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\";\n" +
				"\n" +
				"import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\";\n" +
				"\n" +
				"let $log-in := xmldb:login(\"/db\", \"" + usu + "\", \"" + usuPwd + "\")\n" +
				"let $create-collection := xmldb:create-collection(\"/db\", \"" + nombreColeccion + "\")\n" +
				"for $record in doc('/db/" + nombreColeccion + ".rdf')/rdf:RDF/*\n" +
				"let $split-record :=\n" +
				"    <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
				"        {$record}\n" +
				"    </rdf:RDF>\n" +
				"let $about := $record/@rdf:about\n" +
				"let $filename := util:hash($record/@rdf:about/string(), \"md5\") || \".xml\"\n" +
				"return\n" +
				"    xmldb:store(\"/db/" + nombreColeccion + "\", $filename, $split-record)";

			// Ejecutamos la creación de la colección
			servicio.query(crearColeccion);
			col.close();
			System.out.println("Colección '" + nombreColeccion + "' creada");

			//TODO generarDatosBase();
		} catch (XMLDBException | ClassNotFoundException e) { System.out.println("ERROR AL CONSULTAR DOCUMENTO.");
		} catch (IllegalAccessException | InstantiationException e) { e.printStackTrace(); }
	}
}
```

## AÑADIR XML PARTIDAS A LA COLECCIÓN
```java
public static void insertarXMLPartidas() {
	if (conectar() != null) {
		try {
			int recursosAntes = col.listResources().length;
			int recursosDespues;

			// DOCUMENTACIÓN: http://www.exist-db.org/exist/apps/doc/devguide_xmldb#use-xmldb (storeResource)

			// Inicializamos el recurso
			XMLResource res = null;

			// Creamos el recurso -> recibe 2 parámetros tipo String:
			//                          s: nombre.xml (si lo dejamos null, pondrá un nombre aleatorio)
			//                          s1: tipo recurso (en este caso, siempre será XMLResource)
			res = (XMLResource) col.createResource("partidas.xml", "XMLResource");

			// Elegimos el fichero .xml que queremos añadir a la colección
			File f = new File("./partidas.xml");

			// Fijamos como contenido ese archivo .xml elegido
			res.setContent(f);
			col.storeResource(res); // Lo añadimos a la colección

			recursosDespues = col.listResources().length;

			System.out.println("Recursos XML añadidos a la colección: " + (recursosDespues - recursosAntes));

		} catch (XMLDBException ignored) {
			System.out.println("Error al crear el recurso");
		}
	} else {
		System.out.println("Error al conectar con la BBDD");
	}
}
```