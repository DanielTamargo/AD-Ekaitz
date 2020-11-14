public class Pruebas {
    public static void main(String[] args) {
        System.out.println("declare namespace rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\";\n" +
                "\n" +
                "import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\";\n" +
                "\n" +
                "let $log-in := xmldb:login(\"/db\", \"admin\", \"\")\n" +
                "let $create-collection := xmldb:create-collection(\"/db\", \"output\")\n" +
                "for $record in doc('/db/records.rdf')/rdf:RDF/*\n" +
                "let $split-record :=\n" +
                "    <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
                "        {$record}\n" +
                "    </rdf:RDF>\n" +
                "let $about := $record/@rdf:about\n" +
                "let $filename := util:hash($record/@rdf:about/string(), \"md5\") || \".xml\"\n" +
                "return\n" +
                "    xmldb:store(\"/db/output\", $filename, $split-record)");
    }

}
