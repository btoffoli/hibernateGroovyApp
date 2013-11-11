package br.com.btoffoli.conf

/**
 * Created with IntelliJ IDEA.
 * User: btoffoli
 * Date: 03/11/13
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.Liquibase
import br.com.btoffoli.resource.Resource
import liquibase.resource.ClassLoaderResourceAccessor

class ConfigLiquibase {

    static void config() {

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(Resource.connection);
        Liquibase liquibase = new Liquibase("user-db.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(null);




    }

}
