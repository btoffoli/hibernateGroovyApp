package br.com.btoffoli.conf

/**
 * Created with IntelliJ IDEA.
 * User: btoffoli
 * Date: 03/11/13
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
class ConfigLiquibase {

    static void config() {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setDatabase("jdbc:hsqldb:file:/tmp/hsqldb/testdb");
        dataSource.setUser("SA");
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase("user-db.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(null);


    }

}
