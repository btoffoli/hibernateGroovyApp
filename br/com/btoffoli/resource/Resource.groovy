package br.com.btoffoli.resource

import org.hibernate.SessionFactory
import org.hibernate.connection.ConnectionProvider
import org.hibernate.connection.DatasourceConnectionProvider
import org.hibernate.impl.SessionFactoryImpl

import javax.sql.DataSource
import java.sql.Connection

/**
 * Created with IntelliJ IDEA.
 * User: btoffoli
 * Date: 03/11/13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
class Resource {
    static SessionFactory sessionFactory

    static ConnectionProvider getConnectionProvider() {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory
        sessionFactoryImpl.connectionProvider
    }

    static Connection getConnection() {
        connectionProvider.connection
    }

    static DataSource getDataSource(){
        DatasourceConnectionProvider datasourceConnectionProvider = (DatasourceConnectionProvider) connectionProvider
        datasourceConnectionProvider.dataSource
    }


}
