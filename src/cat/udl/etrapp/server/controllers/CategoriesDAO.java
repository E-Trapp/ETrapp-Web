package cat.udl.etrapp.server.controllers;

public class CategoriesDAO {

    // Get category and its descendents
//    WITH RECURSIVE nodes_cte(id, parent_id, depth, path) AS (
//    SELECT tn.id, tn.parent_id, 1::INT AS depth, tn.id::TEXT AS path FROM categories AS tn WHERE tn.id = 2
//    UNION ALL
//    SELECT c.id, c.parent_id, p.depth + 1 AS depth, (p.path || '->' || c.id::TEXT) FROM nodes_cte AS p, categories AS c WHERE c.parent_id = p.id
//)
//    SELECT * FROM nodes_cte AS n;

}
