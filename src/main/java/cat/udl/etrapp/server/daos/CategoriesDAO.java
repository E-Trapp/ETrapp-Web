package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Category;

import java.sql.*;
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
                    categories.add(categoryFromResultSet(resultSet));
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

    private Category categoryFromResultSet(ResultSet resultSet) throws SQLException {
        final Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        category.setParentId(resultSet.getLong("parent_id"));

        return category;
    }


    public Category getCategoryById(long parentId) {
        final String SQLQuery;
        Category c = null;
        SQLQuery = "SELECT * FROM categories WHERE id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {
            statement.setLong(1, parentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                     c = categoryFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getEvents");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEvents()");
            System.err.println(e.getMessage());
        }

        return c;
    }

    public void createCategory(String categoryTitle) {
            try (Connection connection = DBManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)");
            ) {
                statement.setString(1, categoryTitle);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error in SQL: createEvent()");
                System.err.println(e.getMessage());
            }
    }

    public void deleteCategory(long categoryId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
        ) {
            statement.setLong(1, categoryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in SQL: createEvent()");
            System.err.println(e.getMessage());
        }
    }
}
