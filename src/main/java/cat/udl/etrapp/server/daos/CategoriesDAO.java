package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Category;
import cat.udl.etrapp.server.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO {

    // Get category and its descendents
//    WITH RECURSIVE nodes_cte(id, parent_id, depth, path) AS (
//    SELECT tn.id, tn.parent_id, 1::INT AS depth, tn.id::TEXT AS path FROM categories AS tn WHERE tn.id = 2
//    UNION ALL
//    SELECT c.id, c.parent_id, p.depth + 1 AS depth, (p.path || '->' || c.id::TEXT) FROM nodes_cte AS p, categories AS c WHERE c.parent_id = p.id
//)
//    SELECT * FROM nodes_cte AS n;

    private static CategoriesDAO instance;

    private CategoriesDAO() {
    }

    public static synchronized CategoriesDAO getInstance() {
        if (instance == null) instance = new CategoriesDAO();
        return instance;
    }

    public List<Category> getCategories() {
        final String SQLQuery;

        SQLQuery = "SELECT * FROM categories ORDER BY name ASC";

        final List<Category> categories = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(eventFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getEvents");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEvents()");
            System.err.println(e.getMessage());
        }

        return categories;
    }

    private Category eventFromResultSet(ResultSet resultSet) throws SQLException {
        final Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        category.setParentId(resultSet.getLong("parent_id"));

        return category;
    }


}
